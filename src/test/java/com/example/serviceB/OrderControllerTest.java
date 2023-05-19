package com.example.serviceB;

import com.example.serviceB.Exception.InternalErrorException;
import com.example.serviceB.Exception.OrderNotFoundException;
import com.example.serviceB.controller.OrderController;
import com.example.serviceB.module.Order;
import com.example.serviceB.module.OrderPerCountry;
import com.example.serviceB.module.WeightPerCountry;
import com.example.serviceB.service.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {
    @Mock
    OrderServiceImpl orderServiceImplTest;
    @InjectMocks
    OrderController orderControllerTest;

    List<Order> getOrderList() {
        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.builder().id("1").country("Mozambique").email("email1@gmail.com")
                .parcel_weight(12.2).phone_number("258 852828436").build());
        orderList.add(Order.builder().id("2").country("Cameroon").email("email2@gmail.com")
                .parcel_weight(11.7).phone_number("237 209993809").build());
        return orderList;
    }

    @Test
    public void testSaveOrder() throws Exception {
        doNothing().when(orderServiceImplTest).save(anyList());
        assertEquals("success", orderControllerTest.saveOrders(getOrderList()));

    }

    @Test
    void getOrdersTest() throws OrderNotFoundException {
        when(orderServiceImplTest.getOrders(anyString(), anyString(), anyDouble(), anyInt(), anyInt())).thenReturn(getOrderList());
        List<Order> orderList = orderControllerTest.getOrders("Uganda", "323", 12.2, 0, 10);
        assertEquals(2, orderList.size());
    }

    @Test
    void getOrdersNotFoundExceptionTest() throws OrderNotFoundException {
        doThrow(OrderNotFoundException.class).when(orderServiceImplTest).getOrders(anyString(), anyString(), anyDouble(), anyInt(), anyInt());
        assertThrows(OrderNotFoundException.class, () -> orderControllerTest.getOrders("Uganda", "323", 12.2, 0, 10));
    }

    @Test
    void getTotalOrdersPerCountryTest() throws InternalErrorException {
        List<OrderPerCountry> orders = new ArrayList<>();
        orders.add(new OrderPerCountry("Uganda", 2));
        orders.add(new OrderPerCountry("india", 1));
        when(orderServiceImplTest.getTotalOrdersPerCountry()).thenReturn(orders);
        List<OrderPerCountry> orderPerCountries = orderControllerTest.getTotalOrdersPerCountry();
        assertEquals(2, orderPerCountries.size());
        assertEquals(2, orderPerCountries.get(0).getCount());
    }

    @Test
    void getTotalOrdersPerCountryInternalErrorExceptionTest() throws InternalErrorException {
        doThrow(InternalErrorException.class).when(orderServiceImplTest).getTotalOrdersPerCountry();
        assertThrows(InternalErrorException.class, () -> orderControllerTest.getTotalOrdersPerCountry());
    }

    @Test
    void findTotalWeightPerCountryTest() throws InternalErrorException {
        List<WeightPerCountry> orders = new ArrayList<>();
        orders.add(new WeightPerCountry("Uganda", 2.1));
        orders.add(new WeightPerCountry("india", 1.9));
        when(orderServiceImplTest.getTotalWeightOfOrdersPerCountry()).thenReturn(orders);
        List<WeightPerCountry> weightOfOrdersPerCountry = orderControllerTest.getTotalWeightOfOrdersPerCountry();
        assertEquals(2, weightOfOrdersPerCountry.size());
        assertEquals(2.1, weightOfOrdersPerCountry.get(0).getTotalWeight());

    }

    @Test
    void getTotalWeightOfOrdersPerCountryInternalErrorExceptionTest() throws InternalErrorException {
        doThrow(InternalErrorException.class).when(orderServiceImplTest).getTotalWeightOfOrdersPerCountry();
        assertThrows(InternalErrorException.class, () -> orderControllerTest.getTotalWeightOfOrdersPerCountry());
    }

}
