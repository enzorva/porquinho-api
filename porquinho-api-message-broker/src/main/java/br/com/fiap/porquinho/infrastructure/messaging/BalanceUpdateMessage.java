package br.com.fiap.porquinho.infrastructure.messaging;

import java.io.Serializable;
import java.math.BigDecimal;

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
public class BalanceUpdateMessage implements Serializable {

    private Long accountId;
    private BigDecimal transactionValue;
    private Long transactionId;
    private String description;
}
