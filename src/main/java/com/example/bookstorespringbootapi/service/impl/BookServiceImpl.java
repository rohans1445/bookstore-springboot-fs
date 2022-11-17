package com.example.bookstorespringbootapi.service.impl;

import com.example.bookstorespringbootapi.dto.BookDTO;
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
    public Book saveBook(BookDTO bookDTO) {
        Book book = null;
        if(bookDTO.getId() != 0){
            book = getBookById(bookDTO.getId());
        } else {
            book = new Book();
        }

        book.setBookDetail(bookDTO.getBookDetail());
        book.setAuthor(bookDTO.getAuthor());
        book.setShortDesc(bookDTO.getShortDesc());
        book.setTitle(bookDTO.getTitle());
        book.setPrice(bookDTO.getPrice());
        book.setImgPath(bookDTO.getImgPath());

        return bookRepository.save(book);
    }
}
