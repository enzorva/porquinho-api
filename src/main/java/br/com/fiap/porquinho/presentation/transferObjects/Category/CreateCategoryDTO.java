package br.com.fiap.porquinho.presentation.transferObjects.Category;

import br.com.fiap.porquinho.domainmodel.Category;
import br.com.fiap.porquinho.domainmodel.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CreateCategoryDTO {

    private Long userId;

    @NotBlank(message = "O nome da categoria não pode estar em branco")
    @Size(max = 50, message = "O nome da categoria deve ter no máximo 50 caracteres")
    private String name;

    @NotBlank(message = "O tipo da categoria não pode estar em branco")
    @Pattern(regexp = "^(recipe|expense)$", message = "O tipo deve ser 'recipe' ou 'expense'")
    private String type;

    public static CreateCategoryDTO fromEntity(Category category) {
        if (category == null)
            return null;

        return CreateCategoryDTO.builder()
                .userId(category.getUser().getUserId())
                .name(category.getName())
                .type(category.getType())
                .build();
    }

    public static Category toEntity(CreateCategoryDTO dto, User user) {
        if (dto == null)
            return null;

        return Category.builder()
                .user(user)
                .name(dto.getName())
                .type(dto.getType())
                .build();
    }
}
