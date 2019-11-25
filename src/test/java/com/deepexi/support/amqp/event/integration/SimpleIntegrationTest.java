package com.deepexi.support.amqp.event.integration;

import com.deepexi.support.amqp.event.integration.event.FooEvent;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * [Important] 执行此集成测试前请先在application.yml中配置一个测试用的rabbitmq
 * </br>
 * 测试过程可能会产生一些数据，请避免使用正式环境的mq进行测试
 *
 * @author taccisum - liaojinfeng@deepexi.com
 * @since 2019-11-25
 */
@Ignore
@RunWith(SpringRunner.class)
@Import(SimpleIntegrationTest.Configuration.class)
@SpringBootTest(classes = App.class)
public class SimpleIntegrationTest {

    @TestConfiguration
    public static class Configuration {
        @Bean
        public Exchange exchange() {
            return ExchangeBuilder
                    .topicExchange("test-ex")
                    .build();
        }

        @Bean
        public Queue queue() {
            return new Queue("test-queue");
        }

        @Bean
        public Binding binding() {
            return new Binding(
                    "test-queue",
                    Binding.DestinationType.QUEUE,
                    "test-ex",
                    "key",
                    null
            );
        }
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void index() {
        FooEvent event = new FooEvent();
        event.setStr("val");
        event.setNum(233);
        System.out.println("send message: " + event);
        rabbitTemplate.convertAndSend("test-ex", "key", event);
        FooEvent msg = rabbitTemplate.receiveAndConvert("test-queue", new ParameterizedTypeReference<FooEvent>() {
        });
        System.out.println("consume message: " + msg);
    }
}
