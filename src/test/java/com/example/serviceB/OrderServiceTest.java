package com.example.serviceB;

import com.example.serviceB.Exception.InternalErrorException;
import com.example.serviceB.Exception.OrderNotFoundException;
import com.example.serviceB.module.Order;
import com.example.serviceB.module.OrderPerCountry;
import com.example.serviceB.module.WeightPerCountry;
import com.example.serviceB.repository.OrderRepository;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @InjectMocks
    OrderServiceImpl orderService;
    @Mock
    OrderRepository orderRepository;

    List<Order> getOrderList() {
        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.builder().id("1").country("Mozambique").email("email1@gmail.com")
                .parcel_weight(12.2).phone_number("258 852828436").build());
        orderList.add(Order.builder().id("2").country("Cameroon").email("email2@gmail.com")
                .parcel_weight(11.7).phone_number("237 209993809").build());

        return orderList;
    }

    @Test
    void saveTest() {
        when(orderRepository.saveAll(anyList())).thenReturn(new ArrayList<>());
        orderService.save(getOrderList());
    }

    @Test
    void getOrdersTest() throws OrderNotFoundException {
        when(orderRepository.findALLByCountryOrWeightLimit(anyString(), anyDouble(), any())).thenReturn(getOrderList());
        List<Order> orderList = orderService.getOrders("Uganda", "323", 12.2, 0, 10);
        assertEquals(2, orderList.size());
    }

    @Test
    void getOrdersNotFoundExceptionTest() {
        OrderNotFoundException exception = assertThrows(OrderNotFoundException.class, () -> orderService.getOrders("Uganda", "323", 12.2, 0, 10));
        assertEquals("Order Not found",exception.getMessage());
    }

    @Test
    void getTotalOrdersPerCountryTest() throws InternalErrorException {
        List<OrderPerCountry> orders =new ArrayList<>();
        orders.add(new OrderPerCountry("Uganda",2));
        orders.add(new OrderPerCountry("india",1));
        when(orderRepository.findOrdersPerCountry()).thenReturn(orders);
        List<OrderPerCountry> orderPerCountries = orderService.getTotalOrdersPerCountry();
        assertEquals(2, orderPerCountries.size());
        assertEquals(2,orderPerCountries.get(0).getCount());

    }
    @Test
    void getTotalOrdersPerCountryInternalErrorExceptionTest() throws InternalErrorException {
        doThrow(RuntimeException.class).when(orderRepository).findOrdersPerCountry();
        InternalErrorException ex= assertThrows(InternalErrorException.class, () -> orderService.getTotalOrdersPerCountry());
        assertEquals("Some problem occurred, please try after some time",ex.getMessage());
    }

    @Test
    void findTotalWeightPerCountryTest() throws InternalErrorException {
        List<WeightPerCountry> orders =new ArrayList<>();
        orders.add(new WeightPerCountry("Uganda",2.1));
        orders.add(new WeightPerCountry("india",1.9));
        when(orderRepository.findTotalWeightPerCountry()).thenReturn(orders);
        List<WeightPerCountry> weightOfOrdersPerCountry = orderService.getTotalWeightOfOrdersPerCountry();
        assertEquals(2, weightOfOrdersPerCountry.size());
        assertEquals(2.1,weightOfOrdersPerCountry.get(0).getTotalWeight());

    }

    @Test
    void getTotalWeightOfOrdersPerCountryInternalErrorExceptionTest() {
        doThrow(RuntimeException.class).when(orderRepository).findTotalWeightPerCountry();
        InternalErrorException exception = assertThrows(InternalErrorException.class, () -> orderService.getTotalWeightOfOrdersPerCountry());
        assertEquals("Some problem occurred, please try after some time",exception.getMessage());
    }

}
