package com.example.bookstorespringbootapi.service.impl;

import com.example.bookstorespringbootapi.dto.ExchangeCreateDTO;
import com.example.bookstorespringbootapi.dto.ExchangeRequestDTO;
import com.example.bookstorespringbootapi.entity.ApplicationUser;
import com.example.bookstorespringbootapi.entity.Book;
import com.example.bookstorespringbootapi.entity.ExchangeRequest;
import com.example.bookstorespringbootapi.entity.enums.ExchangeStatus;
import com.example.bookstorespringbootapi.exception.InvalidInputException;
import com.example.bookstorespringbootapi.repository.ExchangeRepository;
import com.example.bookstorespringbootapi.service.BookService;
import com.example.bookstorespringbootapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExchangeService {

    private final UserService userService;
    private final BookService bookService;
    private final ExchangeRepository exchangeRepository;

    public void createNewExchange(ExchangeCreateDTO exchangeCreateDTO){
        ApplicationUser currentUser = userService.getCurrentUser();

        // throw ex if user does owns target book
        if(userService.itemExistsInUserInventory(exchangeCreateDTO.getOpenerExchangeBookId(), currentUser.getId())){
            throw new InvalidInputException("Target book is owned by user");
        }

        // throw ex if user does not own source book
        if(!userService.itemExistsInUserInventory(exchangeCreateDTO.getOpenerOwnedBookId(), currentUser.getId())){
            throw new InvalidInputException("User does not own the given owned book");
        }

        // throw ex if exchange exists for source book
        if(checkIfExchangeRequestExists(currentUser.getId(), exchangeCreateDTO.getOpenerOwnedBookId())){
            throw new InvalidInputException("Exchange already exists");
        }

        ExchangeRequest request = new ExchangeRequest();
        Book openerOwnedBook = bookService.getBookById(exchangeCreateDTO.getOpenerOwnedBookId());
        Book openerExchangeBook = bookService.getBookById(exchangeCreateDTO.getOpenerExchangeBookId());

        request.setExchangeOpener(userService.getCurrentUser());
        request.setOpenerOwnedBook(openerOwnedBook);
        request.setOpenerExchangeBook(openerExchangeBook);
        request.setExchangeStatus(ExchangeStatus.OPEN);

        exchangeRepository.save(request);

    }

    public boolean checkIfExchangeRequestExists(int userId, int openerOwnedBook){
        return exchangeRepository.checkIfExchangeRequestExists(userId, openerOwnedBook);
    }

    public List<ExchangeRequest> getAllOpenExchangeRequests() {
        return exchangeRepository.getAllOpenExchangeRequests();
    }

    public ExchangeRequest getExchangeById(int id){
        Optional<ExchangeRequest> exchangeOp = exchangeRepository.findById(id);
        return exchangeOp.orElseThrow(() -> {throw new InvalidInputException("Could not find exchange by id - "+ id);});
    }

    public void cancelExchangeRequest(int exId) {
        ExchangeRequest exchangeRequest = getExchangeById(exId);
        exchangeRequest.setExchangeStatus(ExchangeStatus.CLOSED);
        exchangeRequest.setExchangeCloser(exchangeRequest.getExchangeOpener());
        exchangeRequest.setClosedAt(LocalDateTime.now());

        exchangeRepository.save(exchangeRequest);
    }

    public void processExchangeRequest(int exId) {
        ApplicationUser currentUser = userService.getCurrentUser();
        ExchangeRequest exchangeRequest = getExchangeById(exId);
        ApplicationUser closerUser = userService.getUserById(currentUser.getId());

        // check if closer owns the openers exchange book
        if(!userService.itemExistsInUserInventory(exchangeRequest.getOpenerExchangeBook().getId(), closerUser.getId())){
            // throw exception if closer does not own book
            throw new InvalidInputException("Closer does not own the opener exchange book");
        }
        // check if closer owns the openers owned book, prevents adding duplicate books to users inventory
        if(userService.itemExistsInUserInventory(exchangeRequest.getOpenerOwnedBook().getId(), closerUser.getId())){
            throw new InvalidInputException("Closer already owns the opener owned book - exchange not possible.");
        }

        userService.removeFromInventory(exchangeRequest.getExchangeOpener().getId(), exchangeRequest.getOpenerOwnedBook().getId());
        userService.removeFromInventory(currentUser.getId(), exchangeRequest.getOpenerExchangeBook().getId());

        userService.addToInventory(exchangeRequest.getExchangeOpener().getId(), exchangeRequest.getOpenerExchangeBook().getId());
        userService.addToInventory(closerUser.getId(), exchangeRequest.getOpenerOwnedBook().getId());

        exchangeRequest.setExchangeCloser(closerUser);
        exchangeRequest.setExchangeStatus(ExchangeStatus.SUCCESS);
        exchangeRequest.setClosedAt(LocalDateTime.now());

        exchangeRepository.save(exchangeRequest);
    }
}
