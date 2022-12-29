package com.example.bookstorespringbootapi.service.impl;

import com.example.bookstorespringbootapi.dto.OrderCreateDTO;
import com.example.bookstorespringbootapi.entity.ApplicationUser;
import com.example.bookstorespringbootapi.entity.Book;
import com.example.bookstorespringbootapi.entity.Discount;
import com.example.bookstorespringbootapi.entity.Order;
import com.example.bookstorespringbootapi.entity.enums.OrderStatus;
import com.example.bookstorespringbootapi.exception.InvalidInputException;
import com.example.bookstorespringbootapi.repository.OrderRepository;
import com.example.bookstorespringbootapi.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserService userService;
    private final OrderRepository orderRepository;
    private final PromoService promoService;
    private final BookService bookService;

    @Override
    public Order createOrder(OrderCreateDTO orderCreateDTO) {

        ApplicationUser currentUser = userService.getCurrentUser();
        String discount = orderCreateDTO.getDiscount();
        double total = 0;

        // convert book ids into book objects
        List<Book> orderItems = orderCreateDTO.getCart()
                .stream()
                .map(bookService::getBookById)
                .collect(Collectors.toList());

        // check if user owns the books
        orderItems.forEach((book) -> {
            if(userService.itemExistsInUserInventory(book.getId())){
                throw new InvalidInputException("User already owns the book: " + book.getId());
            }
        });

        // calculate order total
        for(Book orderItem : orderItems){
            total += orderItem.getPrice();
        }

        // apply discount if exists
        if(discount != null && !discount.equals("")) {
            Discount dsc = promoService.getDiscount(discount);
            total -= dsc.getAmount();
            if(total < 0) total = 0;
            total = Math.round(total * 100);
            total = total/100;
        }

        // populate order object
        Order order = new Order();
        order.setUser(currentUser);
        order.setStatus(OrderStatus.UNPAID);
        order.setPaymentType(orderCreateDTO.getPaymentType());
        order.setOrderItems(orderItems);
        order.setTotal(total);

        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Integer id) {
        Optional<Order> optional = orderRepository.findById(id);
        return optional.orElseThrow(() -> new InvalidInputException("Could not find order with id - " + id));
    }

    @Override
    public Order fulfillOrder(Integer id) {
        Order order = getOrderById(id);
        ApplicationUser user = order.getUser();

        // set order status to PAID
        order.setStatus(OrderStatus.PAID);

        // add books to user inventory
        order.getOrderItems().forEach((book) -> {
            user.getUserInventory().add(book);
        });

        // save order and clear users cart
        orderRepository.save(order);
        userService.clearCart(user.getId());
        return order;
    }

    @Override
    public Order fulfillOrder(Integer id, String receipt) {
        Order order = getOrderById(id);
        ApplicationUser user = order.getUser();

        // set order status to PAID
        order.setStatus(OrderStatus.PAID);
        order.setReceiptUrl(receipt);

        // add books to user inventory
        order.getOrderItems().forEach((book) -> {
            user.getUserInventory().add(book);
        });

        // save order and clear users cart
        orderRepository.save(order);
        userService.clearCart(user.getId());
        return order;
    }

}
