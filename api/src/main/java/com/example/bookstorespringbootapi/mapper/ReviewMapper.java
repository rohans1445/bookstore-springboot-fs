package com.example.bookstorespringbootapi.mapper;

import com.example.bookstorespringbootapi.dto.ReviewResponseDTO;
import com.example.bookstorespringbootapi.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(source = "user.userName", target = "username")
    @Mapping(source = "id", target = "reviewId")
    @Mapping(source = "book.title", target = "bookTitle")
    @Mapping(source = "book.id", target = "bookId")
    ReviewResponseDTO toReviewResponseDTO(Review review);

    List<ReviewResponseDTO> toReviewResponseDTOs(List<Review> reviews);

}
