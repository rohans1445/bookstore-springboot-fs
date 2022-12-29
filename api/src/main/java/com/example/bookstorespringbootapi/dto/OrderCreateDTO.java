package com.example.bookstorespringbootapi.dto;

import com.example.bookstorespringbootapi.entity.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateDTO {
    private List<Integer> cart;
    private String discount;
    private PaymentType paymentType;
}
