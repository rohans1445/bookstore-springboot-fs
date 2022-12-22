package com.example.bookstorespringbootapi.repository;

import com.example.bookstorespringbootapi.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PromoRepository extends JpaRepository<Discount, Integer> {
    Optional<Discount> findByCode(String code);
}
