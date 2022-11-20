package com.example.bookstorespringbootapi.controller;

import com.example.bookstorespringbootapi.dto.ReviewCreateDTO;
import com.example.bookstorespringbootapi.dto.ReviewResponseDTO;
import com.example.bookstorespringbootapi.entity.ApplicationUser;
import com.example.bookstorespringbootapi.entity.Review;
import com.example.bookstorespringbootapi.exception.InvalidInputException;
import com.example.bookstorespringbootapi.mapper.ReviewMapper;
import com.example.bookstorespringbootapi.payload.ApiResponse;
import com.example.bookstorespringbootapi.payload.ReviewRequest;
import com.example.bookstorespringbootapi.payload.ReviewResponse;
import com.example.bookstorespringbootapi.service.ReviewService;
import com.example.bookstorespringbootapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;
    private final ReviewMapper reviewMapper;


    @GetMapping("/books/{bookId}/reviews")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ReviewResponseDTO>> getAllReviewsForBook(@PathVariable("bookId") int bookId){
        List<Review> reviews = reviewService.getAllReviewsForBook(bookId);
        List<ReviewResponseDTO> res = reviewMapper.toReviewResponseDTOs(reviews);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/books/{bookId}/reviews")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ReviewResponseDTO> saveReview(@Valid @RequestBody ReviewCreateDTO reviewCreateDTO, @PathVariable("bookId") int bookId){
        Review newReview = reviewService.saveReview(reviewCreateDTO, bookId);
        ReviewResponseDTO res = reviewMapper.toReviewResponseDTO(newReview);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping("/books/{bookId}/reviews/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable("reviewId") int reviewId){
        Review review = reviewService.getReview(reviewId);
        ApplicationUser user = userService.getCurrentUser();
        if(review.getUser().getId() != user.getId()){
            throw new InvalidInputException("Cannot delete review as it is not made by the current user.");
        }
        reviewService.deleteReview(reviewId);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK.value(), "Review deleted successfully."), HttpStatus.OK);
    }

}
