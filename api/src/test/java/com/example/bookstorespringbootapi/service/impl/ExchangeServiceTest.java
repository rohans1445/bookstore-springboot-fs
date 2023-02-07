package com.example.bookstorespringbootapi.service.impl;

import com.example.bookstorespringbootapi.dto.ExchangeCreateDTO;
import com.example.bookstorespringbootapi.entity.ApplicationUser;
import com.example.bookstorespringbootapi.entity.Book;
import com.example.bookstorespringbootapi.entity.ExchangeRequest;
import com.example.bookstorespringbootapi.entity.enums.ExchangeStatus;
import com.example.bookstorespringbootapi.exception.InvalidInputException;
import com.example.bookstorespringbootapi.repository.ExchangeRepository;
import com.example.bookstorespringbootapi.service.BookService;
import com.example.bookstorespringbootapi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@Slf4j
class ExchangeServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private BookService bookService;

    @Mock
    private ExchangeRepository exchangeRepository;

    @InjectMocks
    private ExchangeService exchangeService;

    private ApplicationUser opener;
    private ApplicationUser closer;
    private Book openerOwnedBook;
    private Book openerExchangeBook;
    private ExchangeRequest exchangeRequest;

    @BeforeEach
    void setUp() {
        opener = new ApplicationUser();
        closer = new ApplicationUser();
        exchangeRequest = new ExchangeRequest();
        opener.setId(1);
        closer.setId(2);
        openerOwnedBook = new Book();
        openerExchangeBook = new Book();
        openerOwnedBook.setId(1);
        openerExchangeBook.setId(2);
        exchangeRequest.setExchangeOpener(opener);
        exchangeRequest.setId(1);
        exchangeRequest.setOpenerOwnedBook(openerOwnedBook);
        exchangeRequest.setOpenerExchangeBook(openerExchangeBook);
        exchangeRequest.setExchangeStatus(ExchangeStatus.OPEN);
    }

    @Test
    void whenExchangeOpenerOwnsTargetBook_thenVerifyExchangeNotCreated() {
        when(userService.getCurrentUser()).thenReturn(opener);
        when(userService.itemExistsInUserInventory(openerExchangeBook.getId(), opener.getId())).thenReturn(true);

        ExchangeCreateDTO exchangeCreateDTO = new ExchangeCreateDTO();
        exchangeCreateDTO.setOpenerOwnedBookId(openerOwnedBook.getId());
        exchangeCreateDTO.setOpenerExchangeBookId(openerExchangeBook.getId());

        ArgumentCaptor<ExchangeRequest> argumentCaptor = ArgumentCaptor.forClass(ExchangeRequest.class);

        assertThatThrownBy(() -> {exchangeService.createNewExchange(exchangeCreateDTO);})
                .isInstanceOf(InvalidInputException.class);

        verify(exchangeRepository, times(0)).save(argumentCaptor.capture());
    }

    @Test
    void whenExchangeOpenerDoesNotOwnSourceBook_thenVerifyExchangeNotCreated() {
        when(userService.getCurrentUser()).thenReturn(opener);
        when(userService.itemExistsInUserInventory(openerOwnedBook.getId(), opener.getId())).thenReturn(false);

        ExchangeCreateDTO exchangeCreateDTO = new ExchangeCreateDTO();
        exchangeCreateDTO.setOpenerOwnedBookId(openerOwnedBook.getId());
        exchangeCreateDTO.setOpenerExchangeBookId(openerExchangeBook.getId());

        ArgumentCaptor<ExchangeRequest> argumentCaptor = ArgumentCaptor.forClass(ExchangeRequest.class);

        assertThatThrownBy(() -> {exchangeService.createNewExchange(exchangeCreateDTO);})
                .isInstanceOf(InvalidInputException.class);

        verify(exchangeRepository, times(0)).save(argumentCaptor.capture());
    }

    @Test
    void whenExchangeAlreadyExistsForABook_thenVerifyExchangeNotCreated() {
        when(userService.getCurrentUser()).thenReturn(opener);
        when(userService.itemExistsInUserInventory(openerExchangeBook.getId(), opener.getId())).thenReturn(false);
        when(userService.itemExistsInUserInventory(openerOwnedBook.getId(), opener.getId())).thenReturn(true);
        when(exchangeService.checkIfExchangeRequestExists(opener.getId(), openerOwnedBook.getId())).thenReturn(true);

        ExchangeCreateDTO exchangeCreateDTO = new ExchangeCreateDTO();
        exchangeCreateDTO.setOpenerOwnedBookId(openerOwnedBook.getId());
        exchangeCreateDTO.setOpenerExchangeBookId(openerExchangeBook.getId());

        ArgumentCaptor<ExchangeRequest> argumentCaptor = ArgumentCaptor.forClass(ExchangeRequest.class);

        assertThatThrownBy(() -> {exchangeService.createNewExchange(exchangeCreateDTO);})
                .isInstanceOf(InvalidInputException.class);

        verify(exchangeRepository, times(0)).save(argumentCaptor.capture());
    }

    @Test
    void whenSubmitExchangeWithValidConditions_thenVerifyExchangeCreated() {
        when(userService.getCurrentUser()).thenReturn(opener);
        when(userService.itemExistsInUserInventory(openerExchangeBook.getId(), opener.getId())).thenReturn(false);
        when(userService.itemExistsInUserInventory(openerOwnedBook.getId(), opener.getId())).thenReturn(true);
        when(exchangeService.checkIfExchangeRequestExists(opener.getId(), openerOwnedBook.getId())).thenReturn(false);

        ExchangeCreateDTO exchangeCreateDTO = new ExchangeCreateDTO();
        exchangeCreateDTO.setOpenerOwnedBookId(openerOwnedBook.getId());
        exchangeCreateDTO.setOpenerExchangeBookId(openerExchangeBook.getId());

        ArgumentCaptor<ExchangeRequest> argumentCaptor = ArgumentCaptor.forClass(ExchangeRequest.class);

        exchangeService.createNewExchange(exchangeCreateDTO);

        verify(exchangeRepository, times(1)).save(argumentCaptor.capture());
    }

    @Test
    void checkIfExchangeRequestExists_thenReturnTrue() {
        when(exchangeRepository.checkIfExchangeRequestExists(opener.getId(), openerOwnedBook.getId()))
                .thenReturn(true);

        exchangeService.checkIfExchangeRequestExists(opener.getId(), openerOwnedBook.getId());

        verify(exchangeRepository, times(1)).checkIfExchangeRequestExists(opener.getId(), openerOwnedBook.getId());
    }

    @Test
    void whenCancelExchangeRequestCreatedByOpener_thenCancelSuccessfully() {
        when(exchangeRepository.findById(exchangeRequest.getId()))
                .thenReturn(Optional.ofNullable(exchangeRequest));

        ArgumentCaptor<ExchangeRequest> argumentCaptor = ArgumentCaptor.forClass(ExchangeRequest.class);

        exchangeService.cancelExchangeRequest(exchangeRequest.getId());

        verify(exchangeRepository).save(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue().getExchangeStatus())
                .isSameAs(ExchangeStatus.CLOSED);

        assertThat(argumentCaptor.getValue().getExchangeCloser().getId())
                .isEqualTo(opener.getId());
    }

    @Test
    void givenExchangeCloserDoesNotOwnOpenerExchangeBook_thenDoNotProcessExchange() {
        when(userService.getCurrentUser()).thenReturn(closer);
        when(userService.itemExistsInUserInventory(openerExchangeBook.getId(), closer.getId())).thenReturn(false);
        when(exchangeRepository.findById(exchangeRequest.getId())).thenReturn(Optional.ofNullable(exchangeRequest));

        assertThatThrownBy(() -> {exchangeService.processExchangeRequest(exchangeRequest.getId());})
                .isInstanceOf(InvalidInputException.class);
    }

    @Test
    void givenExchangeCloserDoesNotOwnOpenerOwnedBook_thenDoNotProcessExchange() {
        when(userService.getCurrentUser()).thenReturn(closer);
        when(userService.itemExistsInUserInventory(openerOwnedBook.getId(), closer.getId())).thenReturn(true);
        when(exchangeRepository.findById(exchangeRequest.getId())).thenReturn(Optional.ofNullable(exchangeRequest));

        assertThatThrownBy(() -> {exchangeService.processExchangeRequest(exchangeRequest.getId());})
                .isInstanceOf(InvalidInputException.class);
    }

    @Test
    void whenProcessExchangeWithValidConditions_thenProcessSuccessfully() {
        when(userService.getCurrentUser()).thenReturn(closer);
        when(userService.itemExistsInUserInventory(openerExchangeBook.getId(), closer.getId())).thenReturn(true);
        when(userService.itemExistsInUserInventory(openerOwnedBook.getId(), closer.getId())).thenReturn(false);
        when(exchangeRepository.findById(exchangeRequest.getId())).thenReturn(Optional.ofNullable(exchangeRequest));

        ArgumentCaptor<ExchangeRequest> exchangeRequestCaptor = ArgumentCaptor.forClass(ExchangeRequest.class);

        exchangeService.processExchangeRequest(exchangeRequest.getId());

        verify(exchangeRepository).save(exchangeRequestCaptor.capture());

        assertThat(exchangeRequestCaptor.getValue().getExchangeCloser().getId())
                .isEqualTo(closer.getId());

        assertThat(exchangeRequestCaptor.getValue().getExchangeStatus())
                .isSameAs(ExchangeStatus.SUCCESS);

    }
}