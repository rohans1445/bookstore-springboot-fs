package com.example.bookstorespringbootapi.controller;

import com.example.bookstorespringbootapi.dto.OrderCreateDTO;
import com.example.bookstorespringbootapi.entity.ApplicationUser;
import com.example.bookstorespringbootapi.entity.Order;
import com.example.bookstorespringbootapi.entity.enums.PaymentType;
import com.example.bookstorespringbootapi.repository.OrderRepository;
import com.example.bookstorespringbootapi.service.OrderService;
import com.example.bookstorespringbootapi.service.PaymentService;
import com.example.bookstorespringbootapi.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.PaymentIntentRetrieveParams;
import com.stripe.param.checkout.SessionListLineItemsParams;
import com.stripe.param.checkout.SessionRetrieveParams;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaymentController {

    private final OrderService orderService;
    private final PaymentService paymentService;
    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final OrderRepository orderRepository;

    @Value("${stripe.webhookEndpointSecret}")
    private String webhookEndpointSecret;

    @Operation(summary = "Initiate payment flow")
    @PostMapping("/process-payment")
    public ResponseEntity<String> processPayment(@RequestBody OrderCreateDTO orderCreateDTO) throws JsonProcessingException {
        ApplicationUser currentUser = userService.getCurrentUser();
        Order order = orderService.createOrder(orderCreateDTO);
        Map<String, String> map = new HashMap<>();
        String message = "";

        switch(order.getPaymentType()){
            case STORE_CREDIT:
                try {
                    paymentService.processStoreCreditPayment(order);
                } catch (Exception e){
                    message = "Payment failed. " + e.getMessage();
                    map.put("message", message);
                    return new ResponseEntity<>(objectMapper.writeValueAsString(map), HttpStatus.BAD_REQUEST);
                }

                orderService.fulfillOrder(order);
                message = "Order created successfully";
                map.put("message", message);
                return new ResponseEntity<>(objectMapper.writeValueAsString(map), HttpStatus.CREATED);

            case CARD:
                try{
                    Session session = paymentService.generateCheckoutSession(order);
                    map.put("session_url", session.getUrl());
                    return new ResponseEntity<>(objectMapper.writeValueAsString(map), HttpStatus.CREATED);
                } catch (Exception e){
                    message = "Could not generate checkout session. " + e.getMessage();
                    orderRepository.delete(order);
                    map.put("message", message);
                    return new ResponseEntity<>(objectMapper.writeValueAsString(map), HttpStatus.BAD_REQUEST);
                }

            default:
                break;
        }

        return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
    }


    /*
        Requires STRIPE WEBHOOK running locally (stripe listen --forward-to <url>)
     */
    @Operation(summary = "Handle Stripe events")
    @PostMapping("/payment-event-handler")
    public ResponseEntity<String> stripeWebhook(@RequestBody String body, @RequestHeader("Stripe-Signature") String sigHeader) throws StripeException, JsonProcessingException {
        Event event = null;

        // verify event came from stripe
        try {
            event = Webhook.constructEvent(
                    body, sigHeader, webhookEndpointSecret
            );
        } catch (SignatureVerificationException e) {
            throw new RuntimeException(e);
        }

        // Deserialize the nested object inside the event
        EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
        StripeObject stripeObject = null;
        if (dataObjectDeserializer.getObject().isPresent()) {
            stripeObject = dataObjectDeserializer.getObject().get();
        } else {
            // Deserialization failed, probably due to an API version mismatch.
            // Refer to the Javadoc documentation on `EventDataObjectDeserializer` for
            // instructions on how to handle this case, or return an error here.
            throw new RuntimeException("Deserialization failed");
        }

        // Handle checkout success event
        if ("checkout.session.completed".equals(event.getType())) {
            Session session = (Session) event.getDataObjectDeserializer().getObject().get();


            /*
            Retrieve PaymentIntent object from completed checkout session with the
            expanded Charge object, to save the receipt url.
             */
            PaymentIntentRetrieveParams pi_params = PaymentIntentRetrieveParams.builder()
                    .addExpand("latest_charge")
                    .build();
            PaymentIntent pi = PaymentIntent.retrieve(session.getPaymentIntent(), pi_params, null);
            Charge charge = pi.getLatestChargeObject();
            String receiptUrl = charge.getReceiptUrl();


            //get order id from checkout session
            Integer bookstore_order_id = objectMapper.readValue(session.getMetadata().get("bookstore_order_id"), Integer.class);

            //populate order details
            Order order = orderService.getOrderById(bookstore_order_id);
            order.setReceiptUrl(receiptUrl);
            order.setStripeSessionId(session.getId());

            //fulfill order
            orderService.fulfillOrder(order);

            System.out.println(">>>>>>>>>>> Order fulfilled!");
            System.out.println(session);
        }

        return new ResponseEntity<>("test", HttpStatus.OK);
    }

}
