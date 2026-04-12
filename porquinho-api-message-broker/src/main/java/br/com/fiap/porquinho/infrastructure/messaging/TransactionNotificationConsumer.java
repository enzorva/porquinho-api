package br.com.fiap.porquinho.infrastructure.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import br.com.fiap.porquinho.infrastructure.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TransactionNotificationConsumer {

    @RabbitListener(queues = RabbitMQConfig.TRANSACTION_NOTIFICATION_QUEUE)
    public void handleTransactionNotification(TransactionNotificationMessage message) {
        log.info("Notificação de transação recebida - ID: {}, Conta: {}, Valor: {}, Status: {}",
                message.getTransactionId(),
                message.getAccountName(),
                message.getTransactionValue(),
                message.getStatus());

        log.info("Processando notificação para transação '{}' de {} na conta '{}'",
                message.getDescription(),
                message.getTransactionValue(),
                message.getAccountName());
    }
}
