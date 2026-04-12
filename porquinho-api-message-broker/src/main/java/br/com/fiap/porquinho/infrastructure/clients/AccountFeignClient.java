package br.com.fiap.porquinho.infrastructure.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.fiap.porquinho.infrastructure.config.FeignConfig;
import br.com.fiap.porquinho.presentation.transferObjects.Account.AccountDTO;

@FeignClient(
        name = "accountClient",
        url = "${app.base-url}",
        path = "/api/v1/accounts",
        configuration = FeignConfig.class
)
public interface AccountFeignClient {

    @GetMapping
    List<AccountDTO> findAll();

    @GetMapping("/{id}")
    AccountDTO findById(@PathVariable("id") Long id);
}
