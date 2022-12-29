package com.example.bookstorespringbootapi.service;

import com.example.bookstorespringbootapi.dto.OrderCreateDTO;
import com.example.bookstorespringbootapi.entity.ApplicationUser;
import com.example.bookstorespringbootapi.entity.Book;
import com.example.bookstorespringbootapi.entity.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(OrderCreateDTO orderCreateDTO);
    Order getOrderById(Integer id);
    Order fulfillOrder(Integer id);
    Order fulfillOrder(Integer id, String receipt);
}
