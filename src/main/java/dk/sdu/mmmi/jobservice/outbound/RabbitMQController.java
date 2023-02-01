package dk.sdu.mmmi.jobservice.outbound;

import dk.sdu.mmmi.jobservice.outbound.config.RabbitMQConfig;
import dk.sdu.mmmi.jobservice.service.interfaces.RabbitMqService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQController implements RabbitMqService {
    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(Object object) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.NEW_JOB_EXCHANGE, RabbitMQConfig.NEW_JOB_ROUTING_KEY, object);
    }
}
