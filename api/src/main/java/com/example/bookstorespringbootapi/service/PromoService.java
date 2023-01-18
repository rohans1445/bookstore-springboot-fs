package com.example.bookstorespringbootapi.service;


import com.example.bookstorespringbootapi.dto.DiscountDTO;
import com.example.bookstorespringbootapi.entity.Discount;

public interface PromoService {
    Discount getDiscount(String discount);

    Discount createDiscount(DiscountDTO discountDTO);

    void deleteDiscount(String discount);

}
