package com.example.bookstorespringbootapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeCreateDTO {
    private int openerOwnedBookId;
    private int openerExchangeBookId;
}
