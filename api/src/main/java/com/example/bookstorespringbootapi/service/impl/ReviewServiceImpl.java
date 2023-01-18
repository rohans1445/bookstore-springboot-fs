package com.example.bookstorespringbootapi.service.impl;

import com.example.bookstorespringbootapi.dto.ReviewCreateDTO;
import com.example.bookstorespringbootapi.entity.ApplicationUser;
import com.example.bookstorespringbootapi.entity.Book;
import com.example.bookstorespringbootapi.entity.Review;
import com.example.bookstorespringbootapi.exception.InvalidInputException;
import com.example.bookstorespringbootapi.exception.ResourceNotFoundException;
import com.example.bookstorespringbootapi.repository.ReviewRepository;
import com.example.bookstorespringbootapi.service.BookService;
import com.example.bookstorespringbootapi.service.ReviewService;
import com.example.bookstorespringbootapi.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final UserService userService;
    private final BookService bookService;
    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(UserService userService, BookService bookService, ReviewRepository reviewRepository) {
        this.userService = userService;
        this.bookService = bookService;
        this.reviewRepository = reviewRepository;
    }


    @Override
    public List<Review> getAllReviewsForBook(int bookId) {
        return reviewRepository.findAllByBookId(bookId);
    }

    @Override
    public Review getReview(int reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(() -> new ResourceNotFoundException("Cannot find Review with id: " + reviewId));
    }

    @Override
    public Review saveReview(ReviewCreateDTO reviewRequest, int bookId) {
        ApplicationUser currentUser = userService.getCurrentUser();
        Book book = bookService.getBookById(bookId);

        if(reviewExists(currentUser.getId(), bookId)){
            throw new InvalidInputException("User has already posted review for book id: " + bookId);
        }

        if(!currentUser.getUserInventory().contains(book)){
            throw new InvalidInputException("User does not own book");
        }

        Review review = new Review();
        review.setUser(currentUser);
        review.setBook(book);
        review.setTitle(reviewRequest.getTitle());
        review.setContent(reviewRequest.getContent());
        review.setRating(reviewRequest.getRating());

        return reviewRepository.save(review);
    }

    public boolean reviewExists(int userId, int bookId) {
        return reviewRepository.checkIfReviewExists(userId, bookId);
    }

    @Override
    public void deleteReview(int reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}
