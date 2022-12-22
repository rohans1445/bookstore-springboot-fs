package com.example.bookstorespringbootapi.service.impl;

import com.example.bookstorespringbootapi.entity.ApplicationUser;
import com.example.bookstorespringbootapi.entity.Book;
import com.example.bookstorespringbootapi.entity.Discount;
import com.example.bookstorespringbootapi.entity.Order;
import com.example.bookstorespringbootapi.entity.enums.PaymentType;
import com.example.bookstorespringbootapi.exception.InvalidInputException;
import com.example.bookstorespringbootapi.repository.OrderRepository;
import com.example.bookstorespringbootapi.service.OrderService;
import com.example.bookstorespringbootapi.service.PromoService;
import com.example.bookstorespringbootapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserService userService;
    private final OrderRepository orderRepository;
    private final PromoService promoService;

    @Override
    @Transactional
    public void createOrder(List<Book> orderItems, String discount) {

        ApplicationUser currentUser = userService.getCurrentUser();
        double total = 0;

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

        // throw exception if insufficient balance
        if(!userService.isBalanceIsSufficient(currentUser, total)){
            throw new InvalidInputException("Insufficient balance");
        }

        // populate order object
        Order order = new Order();
        order.setUser(currentUser);
        order.setPaymentType(PaymentType.STORE_CREDIT);
        order.setOrderItems(orderItems);
        order.setTotal(total);

        // debit amount from user store credit balance and save
        userService.debit(currentUser.getId(), total);

        // add books to user inventory
        orderItems.forEach((book) -> {
            currentUser.getUserInventory().add(book);
        });

        orderRepository.save(order);
        userService.clearCart(currentUser.getId());
    }

}
