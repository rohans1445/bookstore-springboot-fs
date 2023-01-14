package com.example.bookstorespringbootapi.controller;

import com.example.bookstorespringbootapi.dto.BookDTO;
import com.example.bookstorespringbootapi.dto.BookListDTO;
import com.example.bookstorespringbootapi.dto.PagedResponseDTO;
import com.example.bookstorespringbootapi.entity.Book;
import com.example.bookstorespringbootapi.mapper.BookMapper;
import com.example.bookstorespringbootapi.repository.BookRepository;
import com.example.bookstorespringbootapi.service.BookService;
import com.example.bookstorespringbootapi.utility.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final BookRepository bookRepository;

    @Operation(summary = "Save a book")
    @PostMapping("/books")
    public ResponseEntity<BookDTO> saveBook(@Valid @RequestBody BookDTO book){
        Book savedBook = bookService.saveBook(book);
        BookDTO res = bookMapper.toBookDTO(savedBook);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all books paged")
    @GetMapping("/books")
    public ResponseEntity<PagedResponseDTO<BookListDTO>> getBooksPaged(
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) throws InterruptedException {

        PagedResponseDTO<BookListDTO> pagedResponse = bookService.getPagedResponse(page, size);

        return new ResponseEntity<>(pagedResponse, HttpStatus.OK);
    }

    @Operation(summary = "Get all books")
    @GetMapping("/books/all")
    public ResponseEntity<?> getAllBooks(){
        List<Book> allBooks = bookService.getAllBooks();
        List<BookListDTO> res = allBooks.stream().map(book -> {
            return BookListDTO.builder()
                    .id(book.getId())
                    .bookDetail(book.getBookDetail())
                    .price(book.getPrice())
                    .reviewCount(book.getReviews().size())
                    .imgPath(book.getImgPath())
                    .author(book.getAuthor())
                    .shortDesc(book.getShortDesc())
                    .timesPurchased(0)
                    .avgReviews(bookService.getAvgRatingForABook(book))
                    .title(book.getTitle())
                    .build();
        }).collect(Collectors.toList());

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
