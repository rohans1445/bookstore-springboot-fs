package com.example.bookstorespringbootapi.service.impl;

import com.example.bookstorespringbootapi.entity.Book;
import com.example.bookstorespringbootapi.exception.ResourceNotFoundException;
import com.example.bookstorespringbootapi.repository.BookRepository;
import com.example.bookstorespringbootapi.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(int id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        bookOptional.orElseThrow(() -> new ResourceNotFoundException("Cannot find Book with id: " + id));
        return bookOptional.get();
    }

    @Override
    public Book saveBook(Book book) {
        Book savedBook = bookRepository.save(book);
        return savedBook;
    }
}
