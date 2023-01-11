package com.example.bookstorespringbootapi.dto;

import com.example.bookstorespringbootapi.entity.BookDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookListDTO{
    private int id;
    private String title;
    private String author;
    private double price;
    private String shortDesc;
    private String imgPath;
    private BookDetail bookDetail;
    private int reviewCount;
    private double avgReviews;
    private int timesPurchased;
}