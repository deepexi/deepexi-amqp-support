package com.deepexi.support.amqp.integration;

import com.deepexi.support.amqp.event.util.EventMessageUtils;
import com.deepexi.support.amqp.integration.event.Foo1Event;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Y.H.Zhou - zhouyuhang@deepexi.com
 * @since 2020/3/27.
 * <p></p>
 */
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class SimpleIntegrationTest1 {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void sendFoo1Event() throws InterruptedException {
        Foo1Event event = new Foo1Event();
        rabbitTemplate.convertAndSend(EventMessageUtils.getEventExchange(event.getClass()), "default", event);
    }
}
