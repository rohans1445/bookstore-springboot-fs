package com.example.bookstorespringbootapi.controller;

import com.example.bookstorespringbootapi.entity.ApplicationUser;
import com.example.bookstorespringbootapi.entity.Book;
import com.example.bookstorespringbootapi.entity.Review;
import com.example.bookstorespringbootapi.payload.*;
import com.example.bookstorespringbootapi.service.ReviewService;
import com.example.bookstorespringbootapi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final ReviewService reviewService;

    public UserController(UserService userService, ReviewService reviewService) {
        this.userService = userService;
        this.reviewService = reviewService;
    }


    @GetMapping("/me")
    public ResponseEntity<CurrentUserResponse> getCurrentLoggedInUserDetails(){
        CurrentUserResponse res = new CurrentUserResponse(userService.getCurrentUser());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{username}/reviews")
    public ResponseEntity<List<ReviewResponse>> getReviewsOfUser(@PathVariable("username") String username){
        ApplicationUser user = userService.getUserByUserName(username);
        List<Review> reviews = user.getReviews();
        List<ReviewResponse> res = reviews.stream().map(ReviewResponse::new).collect(Collectors.toList());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/me/cart")
    public ResponseEntity<ApiResponse> addBookToCart(@RequestBody CartRequest cartRequest){
        userService.addBookToCart(cartRequest.getBookId());
        ApiResponse res = new ApiResponse(HttpStatus.OK, "Item added to cart.");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/me/cart")
    public ResponseEntity<List<BookResponse>> getUsersCart(){
        ApplicationUser user = userService.getCurrentUser();
        List<Book> cart = user.getCart();
        List<BookResponse> res = cart.stream().map(BookResponse::new).collect(Collectors.toList());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping("/me/cart/{bookId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable("bookId") int bookId){
        userService.removeItemFromCart(bookId);
        ApiResponse res = new ApiResponse(HttpStatus.OK, "Removed item from cart.");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
