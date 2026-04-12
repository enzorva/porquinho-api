package br.com.fiap.porquinho.infrastructure.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import br.com.fiap.porquinho.infrastructure.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionMessageProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendBalanceUpdate(BalanceUpdateMessage message) {
        log.info("Enviando mensagem de atualização de saldo para conta {}: valor {}",
                message.getAccountId(), message.getTransactionValue());

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.BALANCE_UPDATE_ROUTING_KEY,
                message);
    }

    public void sendTransactionNotification(TransactionNotificationMessage message) {
        log.info("Enviando notificação de transação {} para conta {}",
                message.getTransactionId(), message.getAccountId());

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.TRANSACTION_NOTIFICATION_ROUTING_KEY,
                message);
    }
}
