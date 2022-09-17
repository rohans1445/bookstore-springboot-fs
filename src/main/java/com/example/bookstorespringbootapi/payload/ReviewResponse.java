package com.example.bookstorespringbootapi.payload;

import com.example.bookstorespringbootapi.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponse {

    private int reviewId;
    private int bookId;
    private String username;
    private String title;
    private String content;
    private int rating;
    private LocalDateTime timestamp;

    public ReviewResponse(Review review){
        this.reviewId = review.getId();
        this.bookId = review.getBook().getId();
        this.username = review.getUser().getUserName();
        this.title = review.getTitle();
        this.content = review.getContent();
        this.rating = review.getRating();
        this.timestamp = review.getTimestamp();
    }

}
