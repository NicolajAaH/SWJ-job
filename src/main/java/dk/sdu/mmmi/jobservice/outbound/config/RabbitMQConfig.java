package dk.sdu.mmmi.jobservice.outbound.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String NEW_JOB_EXCHANGE = "NEW_JOB_EXCHANGE";
    public static final String NEW_JOB_QUEUE = "NEW_JOB_QUEUE";
    public static final String NEW_JOB_ROUTING_KEY = "new.job";

    @Bean
    Exchange exchange() {
        return ExchangeBuilder.topicExchange(NEW_JOB_EXCHANGE).build();
    }

    @Bean
    Queue queue() {
        return QueueBuilder.nonDurable(NEW_JOB_QUEUE).build();
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(NEW_JOB_ROUTING_KEY);
    }
}
