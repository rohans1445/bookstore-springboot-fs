package com.example.bookstorespringbootapi.controller;

import com.example.bookstorespringbootapi.dto.BookDTO;
import com.example.bookstorespringbootapi.entity.Book;
import com.example.bookstorespringbootapi.mapper.BookMapper;
import com.example.bookstorespringbootapi.payload.BookResponse;
import com.example.bookstorespringbootapi.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    @Operation(summary = "Save a book")
    @PostMapping("/books")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BookDTO> saveBook(@Valid @RequestBody BookDTO book){
        Book savedBook = bookService.saveBook(book);
        BookDTO res = bookMapper.toBookDTO(savedBook);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all books")
    @GetMapping("/books")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAllBooks(){
        List<Book> allBooks = bookService.getAllBooks();
        List<BookDTO> res = bookMapper.toBookDTOs(allBooks);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Get book by id")
    @GetMapping("/books/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable("id") int id){
        BookDTO res = bookMapper.toBookDTO(bookService.getBookById(id));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Update a book")
    @PutMapping("/books")
    public ResponseEntity<BookDTO> updateBook(@Valid @RequestBody BookDTO book){
        Book savedBook = bookService.saveBook(book);
        BookDTO res = bookMapper.toBookDTO(savedBook);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Search books")
    @GetMapping("/books/search")
    public ResponseEntity<List<BookDTO>> searchBookByTitle(@RequestParam String title){
        List<Book> searchResult = bookService.searchByTitle(title);
        List<BookDTO> res = bookMapper.toBookDTOs(searchResult);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
