package com.example.bookstorespringbootapi.service;

import com.example.bookstorespringbootapi.dto.OrderCreateDTO;
import com.example.bookstorespringbootapi.entity.Order;

public interface OrderService {
    Order createOrder(OrderCreateDTO orderCreateDTO);
    Order getOrderById(Integer id);
    Order fulfillOrder(Order order);
}
