package com.example.bookstorespringbootapi.repository;

import com.example.bookstorespringbootapi.entity.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Integer> {
    Optional<ApplicationUser> findByUserName(String username);

    Optional<ApplicationUser> findByEmail(String email);
}
