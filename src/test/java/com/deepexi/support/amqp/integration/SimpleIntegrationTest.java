package com.deepexi.support.amqp.integration;

import com.deepexi.support.amqp.event.EventMessageClassMapper;
import com.deepexi.support.amqp.event.EventMessageTypeMapping;
import com.deepexi.support.amqp.integration.event.BarEvent;
import com.deepexi.support.amqp.integration.event.FooEvent;
import com.deepexi.support.amqp.integration.event.RepeatedEvent;
import com.deepexi.support.amqp.listener.Authenticator;
import com.deepexi.support.amqp.listener.IdempotentValidator;
import com.deepexi.support.amqp.listener.MessageRecorder;
import com.deepexi.support.amqp.listener.SimpleMemoryIdempotentValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.messaging.Message;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

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
            return new Queue("foo-queue");
        }

        @Bean
        public Binding binding() {
            return new Binding(
                    "foo-queue",
                    Binding.DestinationType.QUEUE,
                    "test-ex",
                    "foo-key",
                    null
            );
        }

        @Bean
        public MessageConverter messageConverter(EventMessageClassMapper classMapper) {
            Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
            converter.setClassMapper(classMapper);      // specify class mapper
            return converter;
        }

        @Bean
        public EventMessageClassMapper eventMessageClassMapper(EventMessageTypeMapping eventMessageTypeMapping) {
            return new EventMessageClassMapper(eventMessageTypeMapping);
        }

        @Bean
        public EventMessageTypeMapping eventMessageTypeMapping() {
            return new EventMessageTypeMapping.Builder()
                    .pkg("com.deepexi.support.amqp.integration.event")
                    .build();
        }

        @Bean
        public IdempotentValidator idempotentValidator() {
            return new SimpleMemoryIdempotentValidator(true);
        }

        @Bean
        public Authenticator authenticator() {
            return new Authenticator() {
                @Override
                public void login(Message<?> message) {
                    System.out.println("do login");
                }

                @Override
                public void logout(Message<?> message) {
                    System.out.println("do logout");
                }
            };
        }

        @Bean
        public MessageRecorder messageRecorder() {
            return (message, success, result, error) -> {
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    System.out.println(String.format(
                            "payload: %s. headers: %s. success: %s. result: %s. error: %s",
                            objectMapper.writeValueAsString(message.getPayload()),
                            objectMapper.writeValueAsString(message.getHeaders()),
                            success,
                            result,
                            error
                    ));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            };
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
        rabbitTemplate.convertAndSend("test-ex", "foo-key", event);
        FooEvent msg = rabbitTemplate.receiveAndConvert("foo-queue", new ParameterizedTypeReference<FooEvent>() {
        });
        System.out.println("consume message: " + msg);
        assertThat(msg.getStr()).isEqualTo("val");
        assertThat(msg.getNum()).isEqualTo(233);
    }

    @Test
    public void send4listener() {
        BarEvent event = new BarEvent();
        event.setGreeting("hello");
        System.out.println("send message: " + event);
        rabbitTemplate.convertAndSend("test-ex", "sample-key", event);
    }

    @Test
    public void repeatedEvent() {
        RepeatedEvent event = new RepeatedEvent();
        System.out.println("send message: " + event);
        sendRepeatedEvent(event);
        sendRepeatedEvent(event);
    }

    private void sendRepeatedEvent(RepeatedEvent event) {
        rabbitTemplate.convertAndSend("test-ex", "sample-key", event, message -> {
            message.getMessageProperties().setMessageId("repeated-id");
            return message;
        });
    }
}
