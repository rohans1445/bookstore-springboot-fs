package com.example.bookstorespringbootapi.mapper;

import com.example.bookstorespringbootapi.dto.ExchangeRequestDTO;
import com.example.bookstorespringbootapi.entity.ExchangeRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExchangeMapper {

    @Mapping(source = "exchangeOpener.userName", target = "exchangeOpener")
    @Mapping(source = "exchangeCloser.userName", target = "exchangeCloser")
    @Mapping(source = "openerOwnedBook.id", target = "openerOwnedBookId")
    @Mapping(source = "openerOwnedBook.title", target = "openerOwnedBookTitle")
    @Mapping(source = "openerExchangeBook.id", target = "openerExchangeBookId")
    @Mapping(source = "openerExchangeBook.title", target = "openerExchangeBookTitle")
    ExchangeRequestDTO toExchangeRequestDTO(ExchangeRequest exchangeRequest);
    List<ExchangeRequestDTO> toExchangeRequestDTOs(List<ExchangeRequest> exchangeRequests);
}
