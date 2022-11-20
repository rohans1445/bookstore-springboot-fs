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

    void addBookToCart(int bookId);

    void removeItemFromCart(int bookId);
}
