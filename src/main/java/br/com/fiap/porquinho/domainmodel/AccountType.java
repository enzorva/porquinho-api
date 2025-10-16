package br.com.fiap.porquinho.domainmodel;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "P_ACCOUNT_TYPE")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_type_id")
    private @Setter @Getter Long accountTypeId;

    @Column(name = "name", nullable = false, length = 20)
    private @Setter @Getter String name;

    @Column(name = "label", nullable = false, length = 50)
    private @Setter @Getter String label;

    @Column(name = "overdraft", nullable = false, length = 20)
    private @Setter @Getter String behavior;

    @Override
    public int hashCode() {
        return Objects.hash(accountTypeId);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        AccountType accountType = (AccountType) o;
        return Objects.equals(accountTypeId, accountType.accountTypeId);
    }
    
}

