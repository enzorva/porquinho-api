package br.com.fiap.porquinho.domainmodel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "P_TRANSACTION")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private @Setter @Getter Long transactionId;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private @Setter @Getter Account account;

    @Column(name = "transaction_value", nullable = false, precision = 14, scale = 2)
    private @Setter @Getter BigDecimal transactionValue;

    @Column(name = "description", length = 50)
    private @Setter @Getter String description;

    @Column(name = "transaction_date", nullable = false)
    private @Setter @Getter LocalDate transactionDate;

    @Column(name = "has_occurred", nullable = false, precision = 1)
    private @Setter @Getter Integer hasOccurred;

    @Column(name = "is_auto_confirmed", nullable = false, precision = 1)
    private @Setter @Getter Integer isAutoConfirmed;

    @Column(name = "observation", length = 255)
    private @Setter @Getter String observation;

    @ManyToMany
    @JoinTable(name = "P_TRANSACTION_CATEGORY", joinColumns = @JoinColumn(name = "transaction_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    @Builder.Default
    private @Setter @Getter Set<Category> categories = new HashSet<>();

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
        return Objects.hash(transactionId);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        Transaction transaction = (Transaction) o;
        return Objects.equals(transactionId, transaction.transactionId);
    }

}
