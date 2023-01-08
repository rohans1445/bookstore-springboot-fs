package com.example.bookstorespringbootapi.controller;

import com.example.bookstorespringbootapi.dto.BookDTO;
import com.example.bookstorespringbootapi.entity.ApplicationUser;
import com.example.bookstorespringbootapi.entity.Book;
import com.example.bookstorespringbootapi.mapper.BookMapper;
import com.example.bookstorespringbootapi.payload.ApiResponse;
import com.example.bookstorespringbootapi.payload.BookResponse;
import com.example.bookstorespringbootapi.payload.CartRequest;
import com.example.bookstorespringbootapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CartController {

    private final UserService userService;
    private final BookMapper bookMapper;

    @Operation(summary = "Add book to cart")
    @PostMapping("/cart")
    public ResponseEntity<ApiResponse> addBookToCart(@RequestBody CartRequest cartRequest){
        userService.addBookToCart(cartRequest.getBookId());
        ApiResponse res = new ApiResponse(HttpStatus.OK.value(), "Item added to cart.");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Get a users cart")
    @GetMapping("/cart")
    public ResponseEntity<List<BookDTO>> getUsersCart(){
        ApplicationUser user = userService.getCurrentUser();
        List<Book> cart = user.getCart();
        List<BookDTO> res = bookMapper.toBookDTOs(cart);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Delete a cart item")
    @DeleteMapping("/cart/{bookId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable("bookId") int bookId){
        userService.removeItemFromCart(bookId);
        ApiResponse res = new ApiResponse(HttpStatus.OK.value(), "Removed item from cart.");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
