package dk.sdu.mmmi.jobservice.outbound;

import dk.sdu.mmmi.jobservice.service.interfaces.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaController implements KafkaService {
    private static final String TOPIC = "NEW_JOB";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        kafkaTemplate.send(TOPIC, message);
    }
}
