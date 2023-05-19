package com.example.serviceB.controller;

import com.example.serviceB.Exception.InternalErrorException;
import com.example.serviceB.Exception.OrderNotFoundException;
import com.example.serviceB.module.Order;
import com.example.serviceB.module.OrderPerCountry;
import com.example.serviceB.module.WeightPerCountry;
import com.example.serviceB.service.OrderServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderServiceImpl orderServiceImpl;

    @PostMapping("/save")
    @Operation(summary = "Save the list of orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order saved",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class))}),
            @ApiResponse(responseCode = "500", description = "Error Occurred",
                    content = @Content)})
    public String saveOrders(@RequestBody List<Order> orderList) {
        orderServiceImpl.save(orderList);
        return "success";
    }

    @GetMapping("/get")
    @Operation(summary = "To get the list of orders paginated and with optional filters ○ Per country, per date, and weight limit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the orders",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class))}),
            @ApiResponse(responseCode = "404", description = "Orders not found",
                    content = @Content)})
    public List<Order> getOrders(@RequestParam(required = false) String country,
                                 @RequestParam(required = false) String date,
                                 @RequestParam(required = false) Double weightLimit,
                                 int pageNo, int size) throws OrderNotFoundException {
        return orderServiceImpl.getOrders(country, date, weightLimit, pageNo, size);
    }

    //
    @GetMapping("/getTotalOrders")
    @Operation(summary = "To get the total number of orders per country")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the count",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class))}),
            @ApiResponse(responseCode = "500", description = "Error Occurred",
                    content = @Content)})
    public List<OrderPerCountry> getTotalOrdersPerCountry() throws InternalErrorException {
        return orderServiceImpl.getTotalOrdersPerCountry();
    }

    @GetMapping("/getTotalWeight")
    @Operation(summary = "To get the total weight of orders per country")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found the count",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = List.class))}),
            @ApiResponse(responseCode = "500", description = "Error Occurred",
                    content = @Content)})
    public List<WeightPerCountry> getTotalWeightOfOrdersPerCountry() throws InternalErrorException {
        return orderServiceImpl.getTotalWeightOfOrdersPerCountry();
    }

}
