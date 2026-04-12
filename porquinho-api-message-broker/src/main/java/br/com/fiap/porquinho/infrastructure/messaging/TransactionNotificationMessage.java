package br.com.fiap.porquinho.infrastructure.messaging;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionNotificationMessage implements Serializable {

    private Long transactionId;
    private Long accountId;
    private String accountName;
    private BigDecimal transactionValue;
    private String description;
    private LocalDate transactionDate;
    private String status;
}
