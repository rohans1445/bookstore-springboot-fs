package com.example.bookstorespringbootapi.controller;

import com.example.bookstorespringbootapi.dto.DiscountDTO;
import com.example.bookstorespringbootapi.entity.Discount;
import com.example.bookstorespringbootapi.service.PromoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PromoController {
    private final PromoService promoService;

    @GetMapping("/promo/{promo}")
    public ResponseEntity<?> getDiscount(@PathVariable("promo") String promoCode){
        Discount discount = promoService.getDiscount(promoCode);
        DiscountDTO res = new DiscountDTO(discount.getId(), discount.getCode(), discount.getAmount());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/promo")
    public ResponseEntity<DiscountDTO> createDiscount(@RequestBody DiscountDTO discountDTO){
        Discount createdDiscount = promoService.createDiscount(discountDTO);
        DiscountDTO res = new DiscountDTO(createdDiscount.getId(), createdDiscount.getCode(), createdDiscount.getAmount());
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
}
