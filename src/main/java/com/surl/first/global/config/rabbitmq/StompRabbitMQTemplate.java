package com.surl.first.global.config.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StompRabbitMQTemplate implements StompTemplate{
    private final RabbitTemplate template;

    @Override
    public void convertAndSend(String exchange, String routingKey, Object object){
        template.convertAndSend("amq." + exchange, routingKey, object);
    }
}
