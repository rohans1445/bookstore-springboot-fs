package com.example.bookstorespringbootapi.service;

import com.example.bookstorespringbootapi.dto.ReviewCreateDTO;
import com.example.bookstorespringbootapi.entity.Review;

import java.util.List;

public interface ReviewService {

    List<Review> getAllReviewsForBook(int bookId);

    Review getReview(int reviewId);

    Review saveReview(ReviewCreateDTO review, int bookId);

    boolean reviewExists(int userId, int bookId);

    void deleteReview(int reviewId);
}
