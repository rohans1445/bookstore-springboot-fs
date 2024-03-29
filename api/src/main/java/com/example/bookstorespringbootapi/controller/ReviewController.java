package com.example.bookstorespringbootapi.controller;

import com.example.bookstorespringbootapi.dto.ReviewCreateDTO;
import com.example.bookstorespringbootapi.dto.ReviewResponseDTO;
import com.example.bookstorespringbootapi.entity.ApplicationUser;
import com.example.bookstorespringbootapi.entity.Review;
import com.example.bookstorespringbootapi.exception.InvalidInputException;
import com.example.bookstorespringbootapi.mapper.ReviewMapper;
import com.example.bookstorespringbootapi.payload.ApiResponse;
import com.example.bookstorespringbootapi.service.ReviewService;
import com.example.bookstorespringbootapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;
    private final ReviewMapper reviewMapper;

    @Operation(summary = "Get all reviews for a book")
    @GetMapping("/books/{bookId}/reviews")
    public ResponseEntity<List<ReviewResponseDTO>> getAllReviewsForBook(@PathVariable("bookId") int bookId){
        List<Review> reviews = reviewService.getAllReviewsForBook(bookId);
        List<ReviewResponseDTO> res = reviewMapper.toReviewResponseDTOs(reviews);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Save review")
    @PostMapping("/books/{bookId}/reviews")
    public ResponseEntity<ReviewResponseDTO> saveReview(@Valid @RequestBody ReviewCreateDTO reviewCreateDTO, @PathVariable("bookId") int bookId){
        Review newReview = reviewService.saveReview(reviewCreateDTO, bookId);
        ReviewResponseDTO res = reviewMapper.toReviewResponseDTO(newReview);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Delete a review")
    @DeleteMapping("/books/{bookId}/reviews/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable("reviewId") int reviewId){
        Review review = reviewService.getReview(reviewId);
        ApplicationUser user = userService.getCurrentUser();
        if(review.getUser().getId() != user.getId()){
            throw new InvalidInputException("Cannot delete review. Unauthorized operation.");
        }
        reviewService.deleteReview(reviewId);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK.value(), "Review deleted successfully."), HttpStatus.OK);
    }

}
