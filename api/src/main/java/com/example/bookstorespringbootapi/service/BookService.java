package com.example.bookstorespringbootapi.service;

import com.example.bookstorespringbootapi.dto.BookDTO;
import com.example.bookstorespringbootapi.entity.Book;

import java.util.List;

public interface BookService {

    List<Book> getAllBooks();

    Book getBookById(int id);

    Book saveBook(BookDTO book);

}
