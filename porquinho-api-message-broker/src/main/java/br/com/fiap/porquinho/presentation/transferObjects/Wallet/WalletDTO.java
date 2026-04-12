package br.com.fiap.porquinho.presentation.transferObjects.Wallet;

import java.time.LocalDateTime;

import br.com.fiap.porquinho.domainmodel.User;
import br.com.fiap.porquinho.domainmodel.Wallet;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class WalletDTO {

    private Long walletId;

    private Long userId;

    private String name;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static WalletDTO fromEntity(Wallet wallet) {
        if (wallet == null)
            return null;

        return WalletDTO.builder()
                .walletId(wallet.getWalletId())
                .userId(wallet.getUser().getUserId())
                .name(wallet.getName())
                .description(wallet.getDescription())
                .createdAt(wallet.getCreatedAt())
                .updatedAt(wallet.getUpdatedAt())
                .build();
    }

    public static Wallet toEntity(WalletDTO dto, User user) {
        if (dto == null)
            return null;

        return Wallet.builder()
                .walletId(dto.getWalletId())
                .user(user)
                .name(dto.getName())
                .description(dto.getDescription())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }
}
