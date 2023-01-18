package com.example.bookstorespringbootapi.service.impl;

import com.example.bookstorespringbootapi.entity.ApplicationUser;
import com.example.bookstorespringbootapi.entity.Book;
import com.example.bookstorespringbootapi.entity.Order;
import com.example.bookstorespringbootapi.entity.enums.OrderStatus;
import com.example.bookstorespringbootapi.exception.InvalidInputException;
import com.example.bookstorespringbootapi.service.PaymentService;
import com.example.bookstorespringbootapi.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final UserService userService;
    private final ObjectMapper objectMapper;

    @Value("${stripe.key}")
    private String stripeKey;

    @Value("${ui.baseUrl}")
    private String baseUrl;

    @Override
    @Transactional
    public void processStoreCreditPayment(Order order){

        ApplicationUser currentUser = userService.getCurrentUser();

        // throw exception if insufficient balance
        if(!userService.isBalanceIsSufficient(currentUser, order.getTotal())){
            throw new InvalidInputException("Insufficient balance");
        }

        // debit amount from user store credit balance and save
        userService.debit(currentUser.getId(), order.getTotal());
        order.setStatus(OrderStatus.PAID);
    }

    @Override
    public Session generateCheckoutSession(Order order) throws StripeException, JsonProcessingException {
        ApplicationUser currentUser = userService.getCurrentUser();

        Stripe.apiKey = stripeKey;
        List<SessionCreateParams.LineItem> lineItems = order.getOrderItems()
                .stream()
                .map(book -> SessionCreateParams.LineItem.builder()
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName(book.getTitle())
                                        .setDescription(book.getShortDesc())
                                        .addImage(book.getImgPath())
                                        .build())
                                .setCurrency("usd")
                                .setUnitAmountDecimal(BigDecimal.valueOf(book.getPrice()*100))
                                .build())
                        .setQuantity(1L)
                        .build()).collect(Collectors.toList());

        SessionCreateParams params = SessionCreateParams.builder()
                .addAllLineItem(lineItems)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setCurrency("usd")
                .setAllowPromotionCodes(true)
                .setSuccessUrl(baseUrl + "/payment/success")
                .setCancelUrl(baseUrl + "/checkout?cancelled=true&orderId="+order.getId())
                .putAllMetadata(convertUserDataToMap(currentUser, order))
                .build();

        Session session = Session.create(params);
        log.info(">>> Checkout Session created");
        log.info(String.valueOf(session));
        return session;
    }

    private Map<String, String> convertUserDataToMap(ApplicationUser user, Order order) throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();

        List<Integer> orderItemIds = order.getOrderItems()
                .stream()
                .map(Book::getId)
                .collect(Collectors.toList());

        String orderItems = objectMapper.writeValueAsString(orderItemIds);

        map.put("bookstore_user_id", String.valueOf(user.getId()));
        map.put("bookstore_username", user.getUserName());
        map.put("bookstore_user_email", user.getEmail());
        map.put("bookstore_order_id", String.valueOf(order.getId()));
        map.put("order_items", orderItems);

        return map;
    }


}
