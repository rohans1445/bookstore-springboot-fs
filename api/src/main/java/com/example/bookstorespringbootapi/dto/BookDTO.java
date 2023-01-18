package com.example.bookstorespringbootapi.dto;

import com.example.bookstorespringbootapi.entity.BookDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookDTO {

    private int id;

    @NotBlank(message = "Title cannot be empty")
    @Size(min = 4, max = 100, message = "Title must be between 4 to 50 characters")
    private String title;

    @NotBlank(message = "Author cannot be empty")
    @Size(max = 100, message = "Author name must be between upto 45 characters")
    private String author;

    @Min(value = 0)
    private double price;

    @Size(max = 512)
    private String shortDesc;
    private String imgPath;

    private BookDetail bookDetail;

    private String tags;

}
