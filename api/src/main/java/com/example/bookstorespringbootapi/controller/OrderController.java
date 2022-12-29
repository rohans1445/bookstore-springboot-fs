package com.example.bookstorespringbootapi.controller;

import com.example.bookstorespringbootapi.dto.OrderCreateDTO;
import com.example.bookstorespringbootapi.entity.Book;
import com.example.bookstorespringbootapi.payload.ApiResponse;
import com.example.bookstorespringbootapi.service.BookService;
import com.example.bookstorespringbootapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {

//    private final BookService bookService;
//    private final OrderService orderService;
//
//    @GetMapping("/order/{id}/cancel")
//    public ResponseEntity<ApiResponse> createOrder(@RequestBody OrderCreateDTO orderCreateDTO) {
//
//        orderService.createOrder(orderCreateDTO);
//
//        ApiResponse res = new ApiResponse();
//        res.setStatus(HttpStatus.CREATED.value());
//        res.setMessage("Order created successfully.");
//        return new ResponseEntity<>(res, HttpStatus.CREATED);
//    }

}
