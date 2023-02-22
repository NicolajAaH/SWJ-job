package dk.sdu.mmmi.jobservice.outbound;

import com.google.gson.Gson;
import dk.sdu.mmmi.jobservice.service.interfaces.MqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class MQController implements MqService {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${mq.topic}")
    private String topic;


    private Gson gson = new Gson();

    @Override
    public void sendMessage(Object object) {
        if(!jmsTemplate.isPubSubDomain())
            jmsTemplate.setPubSubDomain(true);
        jmsTemplate.convertAndSend(topic, gson.toJson(object));
    }
}
