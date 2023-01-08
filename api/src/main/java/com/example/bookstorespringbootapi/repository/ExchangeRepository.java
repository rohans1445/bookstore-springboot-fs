package com.example.bookstorespringbootapi.repository;

import com.example.bookstorespringbootapi.entity.ExchangeRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExchangeRepository extends JpaRepository<ExchangeRequest, Integer> {
    @Query("SELECT CASE WHEN COUNT(er) = 1 THEN TRUE ELSE FALSE END " +
            "FROM ExchangeRequest er WHERE er.exchangeOpener.id = :userId AND er.openerOwnedBook.id = :bookId AND er.exchangeStatus = com.example.bookstorespringbootapi.entity.enums.ExchangeStatus.OPEN")
    boolean checkIfExchangeRequestExists(int userId, int bookId);

    @Query("SELECT er FROM ExchangeRequest er WHERE er.exchangeStatus = com.example.bookstorespringbootapi.entity.enums.ExchangeStatus.OPEN")
    List<ExchangeRequest> getAllOpenExchangeRequests();

    @Query("SELECT er FROM ExchangeRequest er WHERE er.exchangeOpener.id = :userId")
    List<ExchangeRequest> getAllExchangeRequestsMadeByUser(int userId);

    @Query("SELECT er FROM ExchangeRequest er WHERE er.exchangeCloser.id = :userId AND er.exchangeStatus = com.example.bookstorespringbootapi.entity.enums.ExchangeStatus.SUCCESS")
    List<ExchangeRequest> getAllExchangeRequestsAcceptedByUser(int userId);
}
