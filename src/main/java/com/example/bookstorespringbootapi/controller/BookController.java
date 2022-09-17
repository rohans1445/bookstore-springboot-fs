package com.example.bookstorespringbootapi.controller;

import com.example.bookstorespringbootapi.entity.Book;
import com.example.bookstorespringbootapi.payload.BookResponse;
import com.example.bookstorespringbootapi.payload.DetailedBookResponse;
import com.example.bookstorespringbootapi.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @PostMapping("/books")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Book> saveBook(@RequestBody Book book){
        Book savedBook = bookService.saveBook(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @GetMapping("/books")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<BookResponse>> getAllBooks(){
        List<BookResponse> bookResponseList = bookService.getAllBooks()
                .stream()
                .map(BookResponse::new).collect(Collectors.toList());
        return new ResponseEntity<>(bookResponseList, HttpStatus.OK);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<DetailedBookResponse> getBookById(@PathVariable("id") int id){
        DetailedBookResponse res = new DetailedBookResponse(bookService.getBookById(id));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
