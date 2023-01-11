package com.example.bookstorespringbootapi.repository;

import com.example.bookstorespringbootapi.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query("SELECT b FROM Book b where b.title LIKE %:title%")
    List<Book> searchByTitle(String title);

    Page<Book> findAll(Pageable pageable);
}
