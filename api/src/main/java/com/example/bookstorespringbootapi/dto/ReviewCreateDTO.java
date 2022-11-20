package com.example.bookstorespringbootapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewCreateDTO {
    @NotBlank(message = "Title must not be blank")
    private String title;
    @NotBlank(message = "Content must not be blank")
    private String content;
    @Min(value = 1, message = "Rating must be between 1 - 5")
    @Max(value = 5, message = "Rating must be between 1 - 5")
    private int rating;
}
