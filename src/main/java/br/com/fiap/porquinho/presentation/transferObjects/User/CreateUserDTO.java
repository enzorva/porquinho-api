package br.com.fiap.porquinho.presentation.transferObjects.User;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.fiap.porquinho.domainmodel.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CreateUserDTO {

    @NotBlank(message = "O nome completo não pode estar em branco")
    @Size(min = 2, max = 200, message = "O nome completo deve ter entre 2 e 200 caracteres")
    private String fullName;

    @NotBlank(message = "O e-mail não pode estar em branco")
    @Email(message = "O e-mail deve ser válido")
    @Size(max = 255, message = "O e-mail deve ter no máximo 255 caracteres")
    private String email;

    @NotBlank(message = "A senha não pode estar em branco")
    @Size(min = 2, max = 255, message = "A senha deve ter entre 2 e 255 caracteres")
    private String password;

    @PositiveOrZero(message = "A renda deve ser maior ou igual a zero")
    private BigDecimal income;

    @Pattern(regexp = "^(masculine|feminine|other)$", message = "O gênero deve ser 'masculine', 'feminine' ou 'other'")
    private String gender;

    @Positive(message = "O número de telefone deve ser positivo")
    private Long phoneNumber;

    private LocalDate birthday;

    @Size(max = 255, message = "A URL da foto de perfil deve ter no máximo 255 caracteres")
    private String profilePictureUrl;

    public static CreateUserDTO fromEntity(User user) {
        if (user == null)
            return null;

        return CreateUserDTO.builder()
                .fullName(user.getFullName())
                .email(user.getEmail())
                .password(user.getHashedPassword())
                .income(user.getIncome())
                .gender(user.getGender())
                .phoneNumber(user.getPhoneNumber())
                .birthday(user.getBirthday())
                .profilePictureUrl(user.getProfilePictureUrl())
                .build();
    }

    public static User toEntity(CreateUserDTO dto) {
        if (dto == null)
            return null;

        return User.builder()
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .hashedPassword(dto.getPassword())
                .income(dto.getIncome())
                .gender(dto.getGender())
                .phoneNumber(dto.getPhoneNumber())
                .birthday(dto.getBirthday())
                .profilePictureUrl(dto.getProfilePictureUrl())
                .build();
    }

}
