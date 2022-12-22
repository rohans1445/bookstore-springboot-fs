package com.example.bookstorespringbootapi.service;

import com.example.bookstorespringbootapi.entity.ApplicationUser;
import com.example.bookstorespringbootapi.entity.Book;
import com.example.bookstorespringbootapi.entity.Order;

import java.util.List;

public interface OrderService {
    void createOrder(List<Book> orderItems, String discount);
}
