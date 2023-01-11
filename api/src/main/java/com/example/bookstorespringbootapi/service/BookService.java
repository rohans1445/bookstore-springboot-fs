package com.example.bookstorespringbootapi.service;

import com.example.bookstorespringbootapi.dto.BookDTO;
import com.example.bookstorespringbootapi.dto.BookListDTO;
import com.example.bookstorespringbootapi.dto.PagedResponseDTO;
import com.example.bookstorespringbootapi.entity.Book;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookService {

    List<Book> getAllBooks();

    Book getBookById(int id);

    Book saveBook(BookDTO book);

    List<Book> searchByTitle(String title);

//    Page<Book> findPaginated(int pageNo, int pageSize);

    double getAvgRatingForABook(Book book);

    PagedResponseDTO<BookListDTO> getPagedResponse(int page, int size);
}
