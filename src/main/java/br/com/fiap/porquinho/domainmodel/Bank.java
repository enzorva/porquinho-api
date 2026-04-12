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
@Table(name = "P_BANK")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_id")
    private @Setter @Getter Long bankId;

    @Column(name = "name", nullable = false, length = 100)
    private @Setter @Getter String name;

    @Column(name = "code", length = 10)
    private @Setter @Getter String code;

    @Column(name = "logo_url", length = 255)
    private @Setter @Getter String logoUrl;

    @Override
    public int hashCode() {
        return Objects.hash(bankId);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        Bank bank = (Bank) o;
        return Objects.equals(bank, bank.bankId);
    }

}
