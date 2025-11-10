package br.com.fiap.porquinho.presentation.transferObjects.Category;

import java.time.LocalDateTime;

import br.com.fiap.porquinho.domainmodel.Category;
import br.com.fiap.porquinho.domainmodel.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CategoryDTO {

    private @Setter @Getter Long categoryId;

    private @Setter @Getter Long userId;

    private @Setter @Getter String name;

    private @Setter @Getter String type;

    private @Setter @Getter LocalDateTime createdAt;

    private @Setter @Getter LocalDateTime updatedAt;

    public static CategoryDTO fromEntity(Category transaction) {
        if (transaction == null)
            return null;

        return CategoryDTO.builder()
                .categoryId(transaction.getCategoryId())
                .userId(transaction.getUser().getUserId())
                .name(transaction.getName())
                .type(transaction.getType())
                .createdAt(transaction.getCreatedAt())
                .updatedAt(transaction.getUpdatedAt())
                .build();
    }

    public static Category toEntity(CategoryDTO dto, User user) {
        if (dto == null)
            return null;

        return Category.builder()
                .categoryId(dto.getCategoryId())
                .user(user)
                .name(dto.getName())
                .type(dto.getType())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }

}
