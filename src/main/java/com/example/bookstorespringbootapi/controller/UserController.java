package com.example.bookstorespringbootapi.controller;

import com.example.bookstorespringbootapi.dto.ReviewResponseDTO;
import com.example.bookstorespringbootapi.entity.ApplicationUser;
import com.example.bookstorespringbootapi.entity.Book;
import com.example.bookstorespringbootapi.entity.Review;
import com.example.bookstorespringbootapi.mapper.ReviewMapper;
import com.example.bookstorespringbootapi.payload.*;
import com.example.bookstorespringbootapi.service.ReviewService;
import com.example.bookstorespringbootapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ReviewMapper reviewMapper;


    @GetMapping("/me")
    public ResponseEntity<CurrentUserResponse> getCurrentLoggedInUserDetails(){
        CurrentUserResponse res = new CurrentUserResponse(userService.getCurrentUser());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{username}/reviews")
    public ResponseEntity<List<ReviewResponseDTO>> getReviewsOfUser(@PathVariable("username") String username){
        ApplicationUser user = userService.getUserByUserName(username);
        List<Review> reviews = user.getReviews();
        List<ReviewResponseDTO> res = reviewMapper.toReviewResponseDTOs(reviews);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
