package com.surl.first.global.config.rabbitmq;

import lombok.RequiredArgsConstructor;

public interface StompTemplate {

    void convertAndSend(String exchange, String routingKey, Object object);
}
