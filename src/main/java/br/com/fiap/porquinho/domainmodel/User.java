package br.com.fiap.porquinho.domainmodel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "P_USER")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private @Setter @Getter Long userId;

    @Column(name = "full_name", nullable = false, length = 200)
    private @Setter @Getter String fullName;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private @Setter @Getter String email;

    @Column(name = "hashed_password", nullable = false, length = 255)
    private @Setter @Getter String hashedPassword;

    @Column(name = "income", nullable = false, precision = 14, scale = 2)
    private @Setter @Getter BigDecimal income;

    @Column(name = "gender", length = 10)
    private @Setter @Getter String gender;

    @Column(name = "phone_number", unique = true)
    private @Setter @Getter Long phoneNumber;

    @Column(name = "birthday")
    private @Setter @Getter LocalDate birthday;

    @Column(name = "profile_picture_url", nullable = false, length = 255)
    private @Setter @Getter String profilePictureUrl;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private @Getter @Setter List<Wallet> wallets;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Builder.Default
    private @Setter @Getter LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private @Setter @Getter LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId);
    }

}
