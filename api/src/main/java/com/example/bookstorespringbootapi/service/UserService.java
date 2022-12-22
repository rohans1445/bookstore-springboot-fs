package com.example.bookstorespringbootapi.service;

import com.example.bookstorespringbootapi.entity.ApplicationUser;
import com.example.bookstorespringbootapi.payload.RegistrationRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    ApplicationUser processRegistrationRequest(RegistrationRequest registrationRequest);

    boolean isUsernameTaken(String username);

    boolean isEmailTaken(String email);

    ApplicationUser getCurrentUser();

    ApplicationUser getUserByUserName(String username);

    ApplicationUser getUserById(int id);

    void addBookToCart(int bookId);

    void removeItemFromCart(int bookId);

    boolean isBalanceIsSufficient(ApplicationUser user, double amount);

    void debit(int id, double amount);

    void credit(int id, double amount);

    boolean itemExistsInUserInventory(int bookId);

    void clearCart(int id);
}
