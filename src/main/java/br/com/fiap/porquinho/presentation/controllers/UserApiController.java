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
import br.com.fiap.porquinho.presentation.transferObjects.CreateUserDTO;
import br.com.fiap.porquinho.presentation.transferObjects.UserDTO;
import br.com.fiap.porquinho.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
@Tag(name = "Usuários", description = "Operações de gerenciamento de usuários: cadastro, consulta, atualização, remoção e listagem de usuários")
public class UserApiController {

    private final UserService<User, Long> userService;

    @Operation(summary = "Listar todos os usuários", description = "Retorna uma lista contendo todos os usuários cadastrados no sistema.")
    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok(userService.findAll()
                .stream()
                .map(UserDTO::fromEntity)
                .toList());
    }

    @Operation(summary = "Buscar usuário por ID", description = "Retorna as informações de um usuário específico com base no seu identificador único.")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        return userService.findById(id)
                .map(user -> ResponseEntity.ok(UserDTO.fromEntity(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Cadastrar novo usuário", description = "Cria um novo registro de usuário no sistema com os dados informados.")
    @PostMapping
    public ResponseEntity<UserDTO> save(@Valid @RequestBody CreateUserDTO createUserDTO) {
        User newUser = userService.create(CreateUserDTO.toEntity(createUserDTO));
        return new ResponseEntity<>(UserDTO.fromEntity(newUser), HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar usuário existente", description = "Atualiza completamente os dados de um usuário já existente com base no ID informado.")
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @Valid @RequestBody CreateUserDTO createUserDTO) {
        if (!userService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado.");
        }

        User user = CreateUserDTO.toEntity(createUserDTO);
        user.setUserId(id);

        User updatedUser = userService.create(user);
        return ResponseEntity.ok(UserDTO.fromEntity(updatedUser));
    }

    @Operation(summary = "Atualizar parcialmente um usuário", description = "Permite atualizar apenas campos específicos de um usuário existente. "
            + "Os campos não informados permanecem inalterados.")
    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> partialUpdate(@PathVariable Long id, @RequestBody CreateUserDTO createUserDTO) {
        try {
            User updatedUser = userService.partialUpdate(id, CreateUserDTO.toEntity(createUserDTO));
            return new ResponseEntity<>(UserDTO.fromEntity(updatedUser), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Remover usuário", description = "Exclui permanentemente um usuário do sistema com base no ID informado.")
    @DeleteMapping
    public ResponseEntity<Void> deleteById(@RequestBody Long id) {
        if (!userService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        userService.removeById(id);
        return ResponseEntity.noContent().build();
    }

}
