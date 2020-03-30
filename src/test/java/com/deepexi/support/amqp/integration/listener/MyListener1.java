package com.deepexi.support.amqp.integration.listener;

import com.deepexi.support.amqp.integration.event.Foo1Event;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Y.H.Zhou - zhouyuhang@deepexi.com
 * @since 2020/3/27.
 * <p></p>
 */

@Component
@RabbitListener(
        bindings = @QueueBinding(
                value = @Queue("my-listener1-queue"),
                exchange = @Exchange(type = ExchangeTypes.TOPIC, name = "${unit-test.exchange}"),
                key = "default"
        )
)
public class MyListener1 {

    @RabbitHandler
    public void foo1Event(Foo1Event event) {
        System.out.println(event);
        assertThat(event.getFoo1()).isEqualTo("foo1");
    }
}
