package br.com.fiap.porquinho.infrastructure.messaging;

import java.math.BigDecimal;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import br.com.fiap.porquinho.domainmodel.Account;
import br.com.fiap.porquinho.infrastructure.config.RabbitMQConfig;
import br.com.fiap.porquinho.service.Account.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class BalanceUpdateConsumer {

    private final AccountService<Account, Long> accountService;

    @RabbitListener(queues = RabbitMQConfig.BALANCE_UPDATE_QUEUE)
    public void handleBalanceUpdate(BalanceUpdateMessage message) {
        log.info("Recebida mensagem de atualização de saldo - Conta: {}, Valor: {}",
                message.getAccountId(), message.getTransactionValue());

        try {
            Account account = accountService.findById(message.getAccountId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Conta não encontrada: " + message.getAccountId()));

            BigDecimal newBalance = account.getBalance().add(message.getTransactionValue());
            account.setBalance(newBalance);
            accountService.create(account);

            log.info("Saldo da conta {} atualizado com sucesso. Novo saldo: {}",
                    message.getAccountId(), newBalance);
        } catch (Exception e) {
            log.error("Erro ao processar atualização de saldo para conta {}: {}",
                    message.getAccountId(), e.getMessage(), e);
        }
    }
}
