package com.example.bookstorespringbootapi.mapper;

import com.example.bookstorespringbootapi.dto.BookDTO;
import com.example.bookstorespringbootapi.entity.Book;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

//    @Mapping(source = "bookDetail.isbn", target = "bookDetail.isbn")
//    @Mapping(source = "bookDetail.publisher", target = "bookDetail.publisher")
//    @Mapping(source = "bookDetail.language", target = "bookDetail.language")
    BookDTO toBookDTO(Book book);

    List<BookDTO> toBookDTOs(List<Book> books);

}
