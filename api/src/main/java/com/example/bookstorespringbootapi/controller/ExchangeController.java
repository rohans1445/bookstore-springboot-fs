package com.example.bookstorespringbootapi.controller;

import com.example.bookstorespringbootapi.dto.ExchangeCreateDTO;
import com.example.bookstorespringbootapi.dto.ExchangeRequestDTO;
import com.example.bookstorespringbootapi.entity.ApplicationUser;
import com.example.bookstorespringbootapi.entity.ExchangeRequest;
import com.example.bookstorespringbootapi.exception.InvalidInputException;
import com.example.bookstorespringbootapi.mapper.ExchangeMapper;
import com.example.bookstorespringbootapi.payload.ApiResponse;
import com.example.bookstorespringbootapi.service.UserService;
import com.example.bookstorespringbootapi.service.impl.ExchangeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ExchangeController {

    private final ExchangeService exchangeService;
    private final UserService userService;
    private final ExchangeMapper exchangeMapper;

    @Operation(summary = "Create a new exchange request")
    @PostMapping("/exchange")
    public ResponseEntity<ApiResponse> createNewExchange(@RequestBody ExchangeCreateDTO exchangeCreateDTO){
        ApiResponse res = new ApiResponse();
        ApplicationUser currentUser = userService.getCurrentUser();

        exchangeService.createNewExchange(exchangeCreateDTO);
        res.setMessage("Exchange created");
        res.setStatus(HttpStatus.CREATED.value());

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all exchanges")
    @GetMapping("/exchange")
    public ResponseEntity<List<ExchangeRequestDTO>> getAllExchanges(){
        ApplicationUser currentUser = userService.getCurrentUser();
        List<ExchangeRequest> allExchanges = exchangeService.getAllOpenExchangeRequests();
        List<ExchangeRequestDTO> res = exchangeMapper.toExchangeRequestDTOs(allExchanges);

        for(ExchangeRequestDTO e : res){
            if(userService.itemExistsInUserInventory(e.getOpenerExchangeBookId(), currentUser.getId()) && !userService.itemExistsInUserInventory(e.getOpenerOwnedBookId(), currentUser.getId())){
                e.setCanExchange(true);
            }
        }

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Cancel an exchange")
    @GetMapping("/exchange/{id}/cancel")
    public ResponseEntity<ApiResponse> cancelExchange(@PathVariable("id") int id){
        ApplicationUser currentUser = userService.getCurrentUser();
        ExchangeRequest ex = exchangeService.getExchangeById(id);
        if(!ex.getExchangeOpener().getUserName().equals(currentUser.getUserName())){
            throw new InvalidInputException("Unauthorized operation");
        }

        exchangeService.cancelExchangeRequest(id);
        ApiResponse res = new ApiResponse();
        res.setMessage("Success");
        res.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Process exchange request")
    @GetMapping("/exchange/{id}/process")
    public ResponseEntity<ApiResponse> processExchange(@PathVariable("id") int exId){
        exchangeService.processExchangeRequest(exId);
        ApiResponse res = new ApiResponse();
        res.setMessage("Success");
        res.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
