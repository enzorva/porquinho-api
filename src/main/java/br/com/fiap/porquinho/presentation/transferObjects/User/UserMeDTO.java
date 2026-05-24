package br.com.fiap.porquinho.presentation.transferObjects.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import br.com.fiap.porquinho.domainmodel.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UserMeDTO {

    private Long userId;

    private String fullName;

    private String email;

    private BigDecimal income;

    private String gender;

    private Long phoneNumber;

    private LocalDate birthday;

    private String profilePictureUrl;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static UserMeDTO fromEntity(User user) {
        if (user == null)
            return null;

        return UserMeDTO.builder()
                .userId(user.getUserId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .income(user.getIncome())
                .gender(user.getGender())
                .phoneNumber(user.getPhoneNumber())
                .birthday(user.getBirthday())
                .profilePictureUrl(user.getProfilePictureUrl())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
