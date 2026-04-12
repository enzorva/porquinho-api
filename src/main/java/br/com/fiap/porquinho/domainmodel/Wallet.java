package br.com.fiap.porquinho.domainmodel;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "P_WALLET")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wallet_id")
    private @Setter @Getter Long walletId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private @Setter @Getter User user;

    @Column(name = "name", length = 50, nullable = false)
    private @Setter @Getter String name;

    @Column(name = "description", length = 100)
    private @Setter @Getter String description;

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
        return Objects.hash(walletId);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        Wallet wallet = (Wallet) o;
        return Objects.equals(walletId, wallet.walletId);
    }

}
