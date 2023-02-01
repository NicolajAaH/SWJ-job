package dk.sdu.mmmi.jobservice.service.interfaces;

public interface RabbitMqService {

    public void sendMessage(Object object);
}
