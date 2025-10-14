package br.com.fiap.porquinho.presentation.transferObjects;

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
public class UserDTO {

    private Long userId;

    private String fullName;

    private String email;

    // private int timeZoneId;

    // private int countryId;

    private BigDecimal income;

    // private int financeObjectiveId;

    private String gender;

    private Long phoneNumber;

    private LocalDate birthday;

    // private int userAddressId;

    private String profilePictureUrl;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static UserDTO fromEntity(User user) {
        if (user == null)
            return null;

        return UserDTO.builder()
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

    public static User toEntity(UserDTO dto) {
        if (dto == null)
            return null;

        return User.builder()
                .userId(dto.getUserId())
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .income(dto.getIncome())
                .gender(dto.getGender())
                .phoneNumber(dto.getPhoneNumber())
                .birthday(dto.getBirthday())
                .profilePictureUrl(dto.getProfilePictureUrl())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }

}
