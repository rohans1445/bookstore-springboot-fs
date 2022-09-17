package com.example.bookstorespringbootapi.controller;

import com.example.bookstorespringbootapi.entity.Review;
import com.example.bookstorespringbootapi.payload.ReviewRequest;
import com.example.bookstorespringbootapi.payload.ReviewResponse;
import com.example.bookstorespringbootapi.service.ReviewService;
import com.example.bookstorespringbootapi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;

    public ReviewController(ReviewService reviewService, UserService userService) {
        this.reviewService = reviewService;
        this.userService = userService;
    }


    @GetMapping("/books/{bookId}/reviews")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ReviewResponse>> getAllReviewsForBook(@PathVariable("bookId") int bookId){
        List<Review> reviews = reviewService.getAllReviewsForBook(bookId);
        List<ReviewResponse> res = reviews.stream().map(review -> new ReviewResponse(
                review.getId(),
                review.getUser().getUserName(),
                review.getTitle(),
                review.getContent(),
                review.getRating(),
                review.getTimestamp())).collect(Collectors.toList());

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/books/{bookId}/reviews")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ReviewResponse> saveReview(@RequestBody ReviewRequest reviewRequest, @PathVariable("bookId") int bookId){
        Review newReview = reviewService.saveReview(reviewRequest, bookId);
        ReviewResponse res = new ReviewResponse(newReview);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
