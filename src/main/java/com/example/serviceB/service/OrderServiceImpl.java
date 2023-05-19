package com.example.serviceB.service;

import com.example.serviceB.Exception.InternalErrorException;
import com.example.serviceB.Exception.OrderNotFoundException;
import com.example.serviceB.module.Order;
import com.example.serviceB.module.OrderPerCountry;
import com.example.serviceB.module.WeightPerCountry;
import com.example.serviceB.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    public void save(List<Order> orderList) {
        orderRepository.saveAll(orderList);
        logger.info("order saved");
    }

    public List<Order> getOrders(String country, String date, Double weightLimit, int pageNo, int size) throws OrderNotFoundException {
        Pageable page = PageRequest.of(pageNo, size);
        List<Order> orderList = orderRepository.findALLByCountryOrWeightLimit(country, weightLimit, page);
        if (orderList.size() == 0) {
            logger.info("Exception: order not found");
            throw new OrderNotFoundException("Order Not found");
        }
        return orderList;
    }

    public List<OrderPerCountry> getTotalOrdersPerCountry() throws InternalErrorException {
        try {
            List<OrderPerCountry> list = orderRepository.findOrdersPerCountry();
            return list;
        } catch (Exception ex) {
            logger.info("Exception: Some problem occurred, please try after some time");
            throw new InternalErrorException("Some problem occurred, please try after some time");
        }
    }

    public List<WeightPerCountry> getTotalWeightOfOrdersPerCountry() throws InternalErrorException {
        try {
            List<WeightPerCountry> list = orderRepository.findTotalWeightPerCountry();
            return list;
        } catch (Exception ex) {
            logger.info("Exception: Some problem occurred, please try after some time");
            throw new InternalErrorException("Some problem occurred, please try after some time");
        }
    }
}
