package br.com.fiap.porquinho.domainmodel;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
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
@Table(name = "P_CATEGORY")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private @Setter @Getter Long categoryId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private @Setter @Getter User user;

    @Column(name = "name", nullable = false, length = 50)
    private @Setter @Getter String name;

    @Column(name = "type", nullable = false, length = 10)
    private @Setter @Getter String type;

    @ManyToMany(mappedBy = "categories")
    @Builder.Default
    private @Setter @Getter Set<Transaction> transactions = new HashSet<>();

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
        return Objects.hash(categoryId);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        Category category = (Category) o;
        return Objects.equals(categoryId, category.categoryId);
    }

}
