package com.example.bookstorespringbootapi.dto;

import com.example.bookstorespringbootapi.entity.Book;
import com.example.bookstorespringbootapi.entity.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDTO {

    private int id;
    private double total;
    private PaymentType paymentType;
    private LocalDateTime createdAt;
    private List<BookDTO> orderItems;

}
