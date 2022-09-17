package com.example.bookstorespringbootapi.service;

import com.example.bookstorespringbootapi.entity.Review;
import com.example.bookstorespringbootapi.payload.ReviewRequest;

import java.util.List;

public interface ReviewService {

    List<Review> getAllReviewsForBook(int bookId);

    Review getReview(int reviewId);

    Review saveReview(ReviewRequest review, int bookId);

    boolean reviewExists(int userId, int bookId);

    void deleteReview(int reviewId);
}
