package br.com.fiap.porquinho.infrastructure.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.fiap.porquinho.infrastructure.config.FeignConfig;
import br.com.fiap.porquinho.presentation.transferObjects.Transaction.TransactionDTO;

@FeignClient(
        name = "transactionClient",
        url = "${app.base-url}",
        path = "/api/v1/transactions",
        configuration = FeignConfig.class
)
public interface TransactionFeignClient {

    @GetMapping
    List<TransactionDTO> findAll();

    @GetMapping("/{id}")
    TransactionDTO findById(@PathVariable("id") Long id);
}
