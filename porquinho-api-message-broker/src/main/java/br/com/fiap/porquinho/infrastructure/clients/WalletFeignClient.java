package br.com.fiap.porquinho.infrastructure.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.fiap.porquinho.infrastructure.config.FeignConfig;
import br.com.fiap.porquinho.presentation.transferObjects.Wallet.WalletDTO;

@FeignClient(
        name = "walletClient",
        url = "${app.base-url}",
        path = "/api/v1/wallets",
        configuration = FeignConfig.class
)
public interface WalletFeignClient {

    @GetMapping
    List<WalletDTO> findAll();

    @GetMapping("/{id}")
    WalletDTO findById(@PathVariable("id") Long id);
}
