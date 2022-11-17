package com.example.bookstorespringbootapi.mapper;

import com.example.bookstorespringbootapi.dto.BookDTO;
import com.example.bookstorespringbootapi.dto.ReviewCreateDTO;
import com.example.bookstorespringbootapi.dto.ReviewResponseDTO;
import com.example.bookstorespringbootapi.entity.Book;
import com.example.bookstorespringbootapi.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(source = "user.userName", target = "username")
    @Mapping(source = "id", target = "reviewId")
    ReviewResponseDTO toReviewResponseDTO(Review review);

    List<ReviewResponseDTO> toReviewResponseDTOs(List<Review> reviews);

}
