package br.com.fiap.porquinho.presentation.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.porquinho.domainmodel.User;
import br.com.fiap.porquinho.domainmodel.Wallet;
import br.com.fiap.porquinho.presentation.transferObjects.Wallet.CreateWalletDTO;
import br.com.fiap.porquinho.presentation.transferObjects.Wallet.WalletDTO;
import br.com.fiap.porquinho.service.User.UserService;
import br.com.fiap.porquinho.service.Wallet.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/wallets")
@Tag(name = "Carteiras", description = "Operações de gerenciamento de carteiras: cadastro, consulta, atualização, remoção e listagem de usuários")
public class WalletApiController {

    private final WalletService<Wallet, Long> walletService;

    private final UserService<User, Long> userService;

    @Operation(summary = "Listar todas as carteiras", description = "Retorna uma lista contendo todas as carteiras cadastradas no sistema.")
    @GetMapping
    public ResponseEntity<List<WalletDTO>> findAll() {
        return ResponseEntity.ok(walletService.findAll()
                .stream()
                .map(WalletDTO::fromEntity)
                .toList());
    }

    @Operation(summary = "Buscar carteira por ID", description = "Retorna as informações de uma carteira específica com base no seu identificador único.")
    @GetMapping("/{id}")
    public ResponseEntity<WalletDTO> findById(@PathVariable Long id) {
        return walletService.findById(id)
                .map(wallet -> ResponseEntity.ok(WalletDTO.fromEntity(wallet)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Cadastrar nova carteira", description = "Cria um novo registro de carteira no sistema com os dados informados.")
    @PostMapping
    public ResponseEntity<WalletDTO> save(@Valid @RequestBody CreateWalletDTO createWalletDTO) {
        User user = userService.findById(createWalletDTO.getUserId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuário não encontrado."));

        Wallet newWallet = walletService.create(CreateWalletDTO.toEntity(createWalletDTO, user));
        return new ResponseEntity<>(WalletDTO.fromEntity(newWallet), HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar carteira existente", description = "Atualiza completamente os dados de uma carteira já existente com base no ID informado.")
    @PutMapping("/{id}")
    public ResponseEntity<WalletDTO> update(@PathVariable Long id,
            @Valid @RequestBody CreateWalletDTO createWalletDTO) {
        if (!walletService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Carteira não encontrado.");
        }

        User user = userService.findById(createWalletDTO.getUserId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuário não encontrado."));

        Wallet wallet = CreateWalletDTO.toEntity(createWalletDTO, user);
        wallet.setWalletId(id);

        Wallet updatedWallet = walletService.create(wallet);
        return ResponseEntity.ok(WalletDTO.fromEntity(updatedWallet));
    }

    @Operation(summary = "Atualizar parcialmente uma carteira", description = "Permite atualizar apenas campos específicos de uma carteira existente. "
            + "Os campos não informados permanecem inalterados.")
    @PatchMapping("/{id}")
    public ResponseEntity<WalletDTO> partialUpdate(@PathVariable Long id,
            @RequestBody CreateWalletDTO createWalletDTO) {
        try {
            Wallet wallet = walletService.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carteira não encontrado."));

            Wallet updatedWallet = walletService.partialUpdate(id,
                    CreateWalletDTO.toEntity(createWalletDTO, wallet.getUser()));
            return new ResponseEntity<>(WalletDTO.fromEntity(updatedWallet), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Remover carteira", description = "Exclui permanentemente uma carteira do sistema com base no ID informado.")
    @DeleteMapping
    public ResponseEntity<Void> deleteById(@RequestBody Long id) {
        if (!walletService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        walletService.removeById(id);
        return ResponseEntity.noContent().build();
    }

}