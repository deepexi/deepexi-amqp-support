package com.deepexi.support.amqp.autoconfigure;

import com.deepexi.support.amqp.event.util.EventMessageUtils;
import com.deepexi.support.amqp.listener.decorated.DecoratedMessageHandlerMethodFactory;
import com.deepexi.support.amqp.listener.decorated.InvocableHandlerMethodDecoration;
import com.deepexi.support.amqp.listener.decorated.InvocableHandlerMethodDecorator;
import com.deepexi.support.amqp.listener.decorator.Authenticator;
import com.deepexi.support.amqp.listener.decorator.IdempotentValidator;
import com.deepexi.support.amqp.listener.decorator.MessageRecorder;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.invocation.InvocableHandlerMethod;

import java.util.List;

/**
 * @author taccisum - liaojinfeng@deepexi.com
 * @since 2020-02-20
 */
@Configuration
public class AmqpSupportAutoConfiguration implements BeanFactoryAware {
    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Bean
    public RabbitListenerConfigurer rabbitListenerConfigurer0(List<InvocableHandlerMethodDecorator> decorators) {
        return registrar -> {
            DecoratedMessageHandlerMethodFactory factory = new DecoratedMessageHandlerMethodFactory(decorators);
            factory.setBeanFactory(beanFactory);
            factory.afterPropertiesSet();
            registrar.setMessageHandlerMethodFactory(factory);
        };
    }

    @Bean
    public InvocableHandlerMethodDecorator invocableHandlerMethodDecorator0(
            IdempotentValidator idempotentValidator,
            Authenticator authenticator0,
            MessageRecorder recorder
    ) {
        return new InvocableHandlerMethodDecorator() {
            @Override
            public InvocableHandlerMethod decorate(InvocableHandlerMethod invocableHandlerMethod) {
                return new InvocableHandlerMethodDecoration(invocableHandlerMethod) {
                    @Override
                    public Object invoke(Message<?> message, Object... providedArgs) throws Exception {
                        boolean success = false;
                        boolean loggedIn = false;
                        Object result = null;
                        Exception error = null;
                        try {
                            boolean repeated = idempotentValidator.isRepeated(message);
                            if (!repeated) {
                                authenticator0.login(message);
                                loggedIn = true;
                                result = super.invoke(message, providedArgs);
                                success = true;
                            }
                            return result;
                        } catch (Exception e) {
                            error = e;
                            // throw exception or handle?
                            throw e;
                        } finally {
                            if (loggedIn) {
                                recorder.record(message, success, result, error);
                                authenticator0.logout(message);
                            }
                        }
                    }
                };
            }

            @Override
            public int getOrder() {
                return Integer.MIN_VALUE;
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public IdempotentValidator idempotentValidator() {
        return new IdempotentValidator.Dummy();
    }

    @Bean
    @ConditionalOnMissingBean
    public Authenticator authenticator0() {
        return new Authenticator.Dummy();
    }

    @Bean
    @ConditionalOnMissingBean
    public MessageRecorder messageRecorder() {
        return new MessageRecorder.Dummy();
    }

    @Bean
    @Primary
    public EventMessageUtils eventMessageUtils() {
        return new EventMessageUtils();
    }
}
