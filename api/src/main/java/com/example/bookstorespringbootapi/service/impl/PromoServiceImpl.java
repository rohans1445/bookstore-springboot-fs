package com.example.bookstorespringbootapi.service.impl;

import com.example.bookstorespringbootapi.dto.DiscountDTO;
import com.example.bookstorespringbootapi.entity.Discount;
import com.example.bookstorespringbootapi.exception.InvalidInputException;
import com.example.bookstorespringbootapi.exception.PaymentException;
import com.example.bookstorespringbootapi.repository.PromoRepository;
import com.example.bookstorespringbootapi.service.PromoService;
import com.stripe.exception.StripeException;
import com.stripe.model.Coupon;
import com.stripe.model.PromotionCode;
import com.stripe.model.PromotionCodeCollection;
import com.stripe.param.CouponCreateParams;
import com.stripe.param.PromotionCodeCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
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

        try{
            CouponCreateParams params = CouponCreateParams.builder()
                    .setName(discountDTO.getCode())
                    .setAmountOff(discountDTO.getAmount().longValue()*100)
                    .setCurrency("usd")
                    .build();

            Coupon coupon = Coupon.create(params);

            PromotionCodeCreateParams promoParams = PromotionCodeCreateParams.builder()
                    .setCode(discountDTO.getCode())
                    .setCoupon(coupon.getId())
                    .setActive(true)
                    .build();

            PromotionCode promotionCode = PromotionCode.create(promoParams);
        } catch (Exception e){
            throw new PaymentException("Error creating promotion code. "+e);
        }

        return promoRepository.save(dsc);
    }

    @Override
    public void deleteDiscount(String code) {
        Discount discount = getDiscount(code);
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("code", code);
            PromotionCodeCollection collection = PromotionCode.list(params);
            collection.getData().get(0).getCoupon().delete();
            promoRepository.delete(discount);
        } catch (Exception e){
            throw new PaymentException("Error deleting promo. "+e);
        }
    }
}
