package br.com.fiap.porquinho.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "porquinho.exchange";

    public static final String BALANCE_UPDATE_QUEUE = "porquinho.balance.update.queue";
    public static final String BALANCE_UPDATE_ROUTING_KEY = "balance.update";

    public static final String TRANSACTION_NOTIFICATION_QUEUE = "porquinho.transaction.notification.queue";
    public static final String TRANSACTION_NOTIFICATION_ROUTING_KEY = "transaction.notification";

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    Queue balanceUpdateQueue() {
        return new Queue(BALANCE_UPDATE_QUEUE, true);
    }

    @Bean
    Queue transactionNotificationQueue() {
        return new Queue(TRANSACTION_NOTIFICATION_QUEUE, true);
    }

    @Bean
    Binding balanceUpdateBinding(Queue balanceUpdateQueue, DirectExchange exchange) {
        return BindingBuilder.bind(balanceUpdateQueue).to(exchange).with(BALANCE_UPDATE_ROUTING_KEY);
    }

    @Bean
    Binding transactionNotificationBinding(Queue transactionNotificationQueue, DirectExchange exchange) {
        return BindingBuilder.bind(transactionNotificationQueue).to(exchange).with(TRANSACTION_NOTIFICATION_ROUTING_KEY);
    }

    @Bean
    MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
