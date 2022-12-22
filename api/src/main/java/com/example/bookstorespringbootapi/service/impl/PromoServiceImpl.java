package com.example.bookstorespringbootapi.service.impl;

import com.example.bookstorespringbootapi.dto.DiscountDTO;
import com.example.bookstorespringbootapi.entity.Discount;
import com.example.bookstorespringbootapi.exception.InvalidInputException;
import com.example.bookstorespringbootapi.repository.PromoRepository;
import com.example.bookstorespringbootapi.service.PromoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PromoServiceImpl implements PromoService {

    private final PromoRepository promoRepository;

    @Override
    public Discount getDiscount(String code) {
        Optional<Discount> discountOptional = promoRepository.findByCode(code);
        discountOptional.orElseThrow(()->{throw new InvalidInputException("Promo code does not exist.");});
        return discountOptional.get();
    }

    @Override
    public Discount createDiscount(DiscountDTO discountDTO) {
        Discount dsc = new Discount();
        dsc.setId(0);
        dsc.setAmount(discountDTO.getAmount());
        dsc.setCode(discountDTO.getCode());
        return promoRepository.save(dsc);
    }
}
