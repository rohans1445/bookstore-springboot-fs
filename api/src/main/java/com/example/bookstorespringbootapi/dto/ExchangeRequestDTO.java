package com.example.bookstorespringbootapi.dto;

import com.example.bookstorespringbootapi.entity.enums.ExchangeStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExchangeRequestDTO {
    private int id;
    private String exchangeOpener;
    private String exchangeCloser;
    private int openerOwnedBookId;
    private String openerOwnedBookTitle;
    private int openerExchangeBookId;
    private String openerExchangeBookTitle;
    private ExchangeStatus exchangeStatus;
    private LocalDateTime createdAt;
    private LocalDateTime closedAt;
    private Boolean canExchange = null;
}
