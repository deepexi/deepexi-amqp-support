package com.deepexi.support.amqp.listener;

import com.deepexi.support.amqp.listener.decorated.DecoratedMessageHandlerMethodFactory;
import com.deepexi.support.amqp.listener.decorated.InvocableHandlerMethodDecoration;
import com.deepexi.support.amqp.listener.decorated.InvocableHandlerMethodDecorator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.invocation.InvocableHandlerMethod;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Component
@Slf4j
public class DecoratedMessageHandlerMethodFactoryTest {

    private DecoratedMessageHandlerMethodFactory factory;

    @Before
    public void setup() {
        List<InvocableHandlerMethodDecorator> decorators = new ArrayList<>();
        decorators.add(new DecoratorA());
        decorators.add(new DecoratorB());
        this.factory = new DecoratedMessageHandlerMethodFactory(decorators);
    }

    @Test
    public void createInvocableHandlerMethod() throws Exception {
        InvocableHandlerMethod invocableHandlerMethod = factory.createInvocableHandlerMethod(
                new DecoratedMessageHandlerMethodFactoryTest(),
                DecoratedMessageHandlerMethodFactoryTest.class.getMethod("method", String.class)
        );

        assertThat(invocableHandlerMethod.invoke(new GenericMessage<>("null"), "1")).isEqualTo("123");
    }

    public static class DecoratorB implements InvocableHandlerMethodDecorator {

        @Override
        public InvocableHandlerMethod decorate(InvocableHandlerMethod invocableHandlerMethod) {
            return new InvocableHandlerMethodDecoration(invocableHandlerMethod) {
                @Override
                public Object invoke(Message<?> message, Object... providedArgs) throws Exception {
                    return super.invoke(message, providedArgs[0] + "2");
                }
            };
        }

        @Override
        public int getOrder() {
            return 0;
        }
    }


    public static class DecoratorA implements InvocableHandlerMethodDecorator {

        @Override
        public InvocableHandlerMethod decorate(InvocableHandlerMethod invocableHandlerMethod) {
            return new InvocableHandlerMethodDecoration(invocableHandlerMethod) {
                @Override
                public Object invoke(Message<?> message, Object... providedArgs) throws Exception {
                    return super.invoke(message, providedArgs[0] + "3");
                }
            };
        }

        @Override
        public int getOrder() {
            return 1;
        }
    }


    public String method(String i) {
        return i;
    }
}