package com.example.bookstorespringbootapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDTO {
    private int reviewId;
    private String username;
    private String title;
    private String content;
    private int rating;
    private LocalDateTime timestamp;
}
