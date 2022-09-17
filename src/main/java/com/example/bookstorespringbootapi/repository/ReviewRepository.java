package com.example.bookstorespringbootapi.repository;

import com.example.bookstorespringbootapi.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findAllByBookId(int bookId);

    @Query("SELECT CASE " +
            "WHEN COUNT(r) > 0 THEN TRUE " +
            "ELSE FALSE END " +
            "FROM Review r " +
            "WHERE r.user.id = :userId AND r.book.id = :bookId")
    boolean checkIfReviewExists(int userId, int bookId);
}
