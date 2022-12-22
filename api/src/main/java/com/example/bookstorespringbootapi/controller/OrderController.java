package com.example.bookstorespringbootapi.controller;

import com.example.bookstorespringbootapi.dto.OrderCreateDTO;
import com.example.bookstorespringbootapi.entity.Book;
import com.example.bookstorespringbootapi.payload.ApiResponse;
import com.example.bookstorespringbootapi.service.BookService;
import com.example.bookstorespringbootapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {

    private final BookService bookService;
    private final OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<ApiResponse> createOrder(@RequestBody OrderCreateDTO orderCreateDTO) {
        List<Book> orderItems = orderCreateDTO.getCart()
                .stream()
                .map(bookService::getBookById)
                .collect(Collectors.toList());

        orderService.createOrder(orderItems, orderCreateDTO.getDiscount());

        ApiResponse res = new ApiResponse();
        res.setStatus(HttpStatus.CREATED.value());
        res.setMessage("Order created successfully.");
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

}
