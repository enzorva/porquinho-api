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

import br.com.fiap.porquinho.domainmodel.Category;
import br.com.fiap.porquinho.domainmodel.User;
import br.com.fiap.porquinho.presentation.transferObjects.Category.CategoryDTO;
import br.com.fiap.porquinho.presentation.transferObjects.Category.CreateCategoryDTO;
import br.com.fiap.porquinho.service.Category.CategoryService;
import br.com.fiap.porquinho.service.User.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/categories")
@Tag(name = "Categorias", description = "Operações de gerenciamento de categorias: cadastro, consulta, atualização, remoção e listagem de categorias")
public class CategoryApiController {

    private final CategoryService<Category, Long> categoryService;
    private final UserService<User, Long> userService;

    @Operation(summary = "Listar todas as categorias", description = "Retorna uma lista contendo todas as categorias cadastradas no sistema.")
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll() {
        return ResponseEntity.ok(categoryService.findAll()
                .stream()
                .map(CategoryDTO::fromEntity)
                .toList());
    }

    @Operation(summary = "Buscar categoria por ID", description = "Retorna as informações de uma categoria específica com base no seu identificador único.")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
        return categoryService.findById(id)
                .map(category -> ResponseEntity.ok(CategoryDTO.fromEntity(category)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Cadastrar nova categoria", description = "Cria um novo registro de categoria no sistema com os dados informados.")
    @PostMapping
    public ResponseEntity<CategoryDTO> save(@Valid @RequestBody CreateCategoryDTO createCategoryDTO) {
        User user = null;
        if (createCategoryDTO.getUserId() != null) {
            user = userService.findById(createCategoryDTO.getUserId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Categoria não encontrada."));
        }

        Category newCategory = categoryService.create(
                CreateCategoryDTO.toEntity(createCategoryDTO, user));

        return new ResponseEntity<>(CategoryDTO.fromEntity(newCategory), HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar categoria existente", description = "Atualiza completamente os dados de uma categoria já existente com base no ID informado.")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable Long id,
            @Valid @RequestBody CreateCategoryDTO createCategoryDTO) {
        if (!categoryService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada.");
        }

        User user = null;
        if (createCategoryDTO.getUserId() != null) {
            user = userService.findById(createCategoryDTO.getUserId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado."));
        }

        Category category = CreateCategoryDTO.toEntity(createCategoryDTO, user);
        category.setCategoryId(id);

        Category updatedCategory = categoryService.create(category);
        return ResponseEntity.ok(CategoryDTO.fromEntity(updatedCategory));
    }

    @Operation(summary = "Atualizar parcialmente uma categoria", description = "Permite atualizar apenas campos específicos de uma categoria existente. "
            + "Os campos não informados permanecem inalterados.")
    @PatchMapping("/{id}")
    public ResponseEntity<CategoryDTO> partialUpdate(@PathVariable Long id,
            @RequestBody CreateCategoryDTO createCategoryDTO) {
        try {
            Category category = categoryService.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada."));

            Category updatedCategory = categoryService.partialUpdate(id,
                    CreateCategoryDTO.toEntity(createCategoryDTO,
                            category.getUser()));

            return new ResponseEntity<>(CategoryDTO.fromEntity(updatedCategory), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Remover categoria", description = "Exclui permanentemente uma categoria do sistema com base no ID informado.")
    @DeleteMapping
    public ResponseEntity<Void> deleteById(@RequestBody Long id) {
        if (!categoryService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        categoryService.removeById(id);
        return ResponseEntity.noContent().build();
    }
}
