package com.example.bookstorespringbootapi.payload;

import com.example.bookstorespringbootapi.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailedBookResponse {
    private int id;
    private String title;
    private String author;
    private double price;
    private String imgPath;
    private String isbn;
    private String publisher;
    private String language;

    public DetailedBookResponse(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.price = book.getPrice();
        this.imgPath = book.getImgPath();
        this.isbn = book.getBookDetail().getIsbn();
        this.publisher = book.getBookDetail().getPublisher();
        this.language = book.getBookDetail().getLanguage();
    }
}
