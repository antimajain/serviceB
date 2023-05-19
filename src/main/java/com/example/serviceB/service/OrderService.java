package com.example.serviceB.service;

import com.example.serviceB.Exception.InternalErrorException;
import com.example.serviceB.Exception.OrderNotFoundException;
import com.example.serviceB.module.Order;
import com.example.serviceB.module.OrderPerCountry;
import com.example.serviceB.module.WeightPerCountry;

import java.util.List;

public interface OrderService {
    void save(List<Order> orderList);

    List<Order> getOrders(String country, String date, Double weightLimit, int pageNo, int size) throws OrderNotFoundException;

    List<OrderPerCountry> getTotalOrdersPerCountry() throws InternalErrorException;

    List<WeightPerCountry> getTotalWeightOfOrdersPerCountry() throws InternalErrorException;

}
