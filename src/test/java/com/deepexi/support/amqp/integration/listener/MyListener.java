package com.deepexi.support.amqp.integration.listener;

import com.deepexi.support.amqp.integration.event.BarEvent;
import com.deepexi.support.amqp.integration.event.RepeatedEvent;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author taccisum - liaojinfeng@deepexi.com
 * @since 2020-02-21
 */
@Component
@RabbitListener(
        bindings = @QueueBinding(
                value = @Queue("sample-queue"),
                exchange = @Exchange(type = ExchangeTypes.TOPIC, name = "test-ex"),
                key = "sample-key"
        )
)
public class MyListener {
    @RabbitHandler
    public void handle(BarEvent event) {
        System.out.println(event);
        assertThat(event.getGreeting()).isEqualTo("hello");
    }

    @RabbitHandler
    public void handle(RepeatedEvent event) {
        System.out.println(event);
    }
}
