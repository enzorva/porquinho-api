package br.com.fiap.porquinho.infrastructure.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.fiap.porquinho.domainmodel.AccountType;
import br.com.fiap.porquinho.domainmodel.Bank;
import br.com.fiap.porquinho.domainmodel.repositories.AccountTypeRepository;
import br.com.fiap.porquinho.domainmodel.repositories.BankRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDataBanks(BankRepository bankRepository) {
        return args -> {
            List<Bank> banks = List.of(
                    Bank.builder().name("Nubank").code("260").build(),
                    Bank.builder().name("Itaú Unibanco").code("341").build(),
                    Bank.builder().name("Bradesco").code("237").build(),
                    Bank.builder().name("Banco do Brasil").code("001").build(),
                    Bank.builder().name("Santander").code("033").build(),
                    Bank.builder().name("Caixa Econômica Federal").code("104").build());

            bankRepository.saveAll(banks);
        };
    }

    @Bean
    CommandLineRunner initAccountTypes(AccountTypeRepository accountTypeRepository) {
        return args -> {
            List<AccountType> types = List.of(
                    AccountType.builder()
                            .name("Conta Corrente")
                            .label("Conta tradicional para movimentações diárias")
                            .behavior("banking_account")
                            .build(),
                    AccountType.builder()
                            .name("Conta Poupança")
                            .label("Conta voltada para rendimentos e economia")
                            .behavior("banking_account")
                            .build(),
                    AccountType.builder()
                            .name("Conta Salário")
                            .label("Conta exclusiva para recebimento de salário")
                            .behavior("banking_account")
                            .build(),
                    AccountType.builder()
                            .name("Conta Investimento")
                            .label("Conta destinada a aplicações financeiras")
                            .behavior("banking_account")
                            .build());

            accountTypeRepository.saveAll(types);
        };
    }

}