package com.surl.first.global.config.kafka;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
@Profile("kafka")
public class KafkaConfig {

    @Bean
    public RecordMessageConverter converter(){
        // 컨버터가 자동으로 javaTimeModule을 넣어준다. 별도로 넣을 필요 없음.
        return new JsonMessageConverter();
    }

    @Bean
    public CommonErrorHandler errorHandler(KafkaOperations<Object, Object> template){
        return new DefaultErrorHandler(
                new DeadLetterPublishingRecoverer(template), new FixedBackOff(1000L, 3));
    }
}
