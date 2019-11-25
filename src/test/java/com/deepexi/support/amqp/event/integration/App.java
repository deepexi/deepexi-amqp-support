package com.deepexi.support.amqp.event.integration;

import com.deepexi.support.amqp.event.EventMessageClassMapper;
import com.deepexi.support.amqp.event.EventMessageTypeMapping;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author taccisum - liaojinfeng@deepexi.com
 * @since 2019-11-25
 */
@SpringBootApplication
@Configuration
public class App {
    @Bean
    public MessageConverter messageConverter(EventMessageClassMapper classMapper) {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        converter.setClassMapper(classMapper);
        return converter;
    }

    @Bean
    public EventMessageClassMapper eventMessageClassMapper(EventMessageTypeMapping eventMessageTypeMapping) {
        return new EventMessageClassMapper(eventMessageTypeMapping);
    }

    @Bean
    public EventMessageTypeMapping eventMessageTypeMapping() {
        return new EventMessageTypeMapping.Builder()
                .pkg(this.getClass().getPackage().getName() + ".event")
                .build();
    }
}
