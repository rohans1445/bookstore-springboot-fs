package com.example.bookstorespringbootapi.payload;

import com.example.bookstorespringbootapi.entity.Book;
import com.example.bookstorespringbootapi.entity.BookDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse {
    private int id;
    private String title;
    private String author;
    private double price;
    private String shortDesc;
    private String imgPath;
    private BookDetail bookDetail;

    public BookResponse(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.price = book.getPrice();
        this.shortDesc = book.getShortDesc();
        this.imgPath = book.getImgPath();
        this.bookDetail = book.getBookDetail();
    }
}
