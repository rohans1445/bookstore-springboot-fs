package com.example.bookstorespringbootapi.service.impl;

import com.example.bookstorespringbootapi.dto.UserUpdateDTO;
import com.example.bookstorespringbootapi.entity.ApplicationUser;
import com.example.bookstorespringbootapi.entity.Book;
import com.example.bookstorespringbootapi.exception.InvalidInputException;
import com.example.bookstorespringbootapi.exception.ResourceNotFoundException;
import com.example.bookstorespringbootapi.payload.RegistrationRequest;
import com.example.bookstorespringbootapi.repository.ApplicationUserRepository;
import com.example.bookstorespringbootapi.security.ApplicationUserDetails;
import com.example.bookstorespringbootapi.service.BookService;
import com.example.bookstorespringbootapi.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final ApplicationUserRepository applicationUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final BookService bookService;

    public UserServiceImpl(ApplicationUserRepository applicationUserRepository, PasswordEncoder passwordEncoder, @Lazy BookService bookService) {
        this.applicationUserRepository = applicationUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.bookService = bookService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ApplicationUser> userOptional = applicationUserRepository.findByUserName(username);
        userOptional.orElseThrow(() -> new UsernameNotFoundException("User name [" + username + "] not found."));
        return new ApplicationUserDetails(userOptional.get());
    }

    @Override
    public ApplicationUser processRegistrationRequest(RegistrationRequest registrationRequest) {
        ApplicationUser user = new ApplicationUser();
        user.setUserName(registrationRequest.getUsername());
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setFirstName(registrationRequest.getFirstName());
        user.setLastName(registrationRequest.getLastName());
        user.setRoles("ROLE_USER");

        return applicationUserRepository.save(user);
    }

    @Override
    public boolean isUsernameTaken(String username) {
        Optional<ApplicationUser> user = applicationUserRepository.findByUserName(username);
        return user.isPresent();
    }

    @Override
    public boolean isEmailTaken(String email) {
        Optional<ApplicationUser> user = applicationUserRepository.findByEmail(email);
        return user.isPresent();
    }

    @Override
    public ApplicationUser getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Optional<ApplicationUser> userOptional = applicationUserRepository.findByUserName(username);
        return userOptional.orElseThrow(() -> new UsernameNotFoundException("No user found with username: "+ username));
    }

    @Override
    public ApplicationUser getUserByUserName(String username) {
        return applicationUserRepository.findByUserName(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
    }

    @Override
    public ApplicationUser getUserById(int id) {
        return applicationUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Override
    public void addBookToCart(int bookId) {
        ApplicationUser user = getCurrentUser();
        Book book = bookService.getBookById(bookId);

        if(user.getCart().contains(book)){
            throw new InvalidInputException("Item is already in cart.");
        }

        user.getCart().add(book);
        applicationUserRepository.save(user);
    }

    @Override
    @Transactional
    public void removeItemFromCart(int bookId) {
        ApplicationUser user = getCurrentUser();
        user.getCart().removeIf(b -> b.getId() == bookId);
    }

    @Override
    public boolean isBalanceIsSufficient(ApplicationUser user, double amount) {
        return user.getCredits() > amount;
    }

    @Override
    @Transactional
    public void debit(int id, double amount) {
        ApplicationUser user = getUserById(id);
        if(amount > user.getCredits())
            throw new InvalidInputException("Insufficient balance - cannot debit for user with id: " + id);

        user.setCredits(user.getCredits() - amount);
    }

    @Override
    @Transactional
    public void credit(int id, double amount){
        ApplicationUser user = getUserById(id);
        user.setCredits(user.getCredits() + amount);
    }

    @Override
    public boolean itemExistsInUserInventory(int bookId, int userId) {
        ApplicationUser currentUser = getUserById(userId);
        Book book = bookService.getBookById(bookId);
        return currentUser.getUserInventory().contains(book);
    }

    @Override
    public void clearCart(int id) {
        ApplicationUser user = getUserById(id);
        user.setCart(new ArrayList<>());
        applicationUserRepository.save(user);
    }

    @Override
    public void updateUser(String username, UserUpdateDTO userUpdateDTO) {
        ApplicationUser user = getUserByUserName(username);

        if(StringUtils.hasText(userUpdateDTO.getFirstName())) user.setFirstName(userUpdateDTO.getFirstName());
        if(StringUtils.hasText(userUpdateDTO.getLastName())) user.setLastName(userUpdateDTO.getLastName());
        if(StringUtils.hasText(userUpdateDTO.getEmail())) user.setEmail(userUpdateDTO.getEmail());
        if(StringUtils.hasText(userUpdateDTO.getPassword())) user.setPassword(passwordEncoder.encode(userUpdateDTO.getPassword()));
        if(StringUtils.hasText(userUpdateDTO.getUserImg())) user.setUserImg(userUpdateDTO.getUserImg());
        if(StringUtils.hasText(userUpdateDTO.getUsername())){
            if(isUsernameTaken(userUpdateDTO.getUsername())){
                throw new InvalidInputException("This username is taken");
            }
            user.setUserName(userUpdateDTO.getUsername());
        }
        applicationUserRepository.save(user);
    }

    @Override
    @Transactional
    public void removeFromInventory(int userId, int bookId) {
        ApplicationUser user = getUserById(userId);
        Book book = bookService.getBookById(bookId);
        user.getUserInventory().remove(book);
    }

    @Override
    @Transactional
    public void addToInventory(int userId, int bookId) {
        ApplicationUser user = getUserById(userId);
        Book book = bookService.getBookById(bookId);
        user.getUserInventory().add(book);
    }


}
