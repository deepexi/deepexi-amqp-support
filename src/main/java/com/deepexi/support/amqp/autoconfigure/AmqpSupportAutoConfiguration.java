package com.deepexi.support.amqp.autoconfigure;

import com.deepexi.support.amqp.listener.DecoratedMessageHandlerMethodFactory;
import com.deepexi.support.amqp.listener.InvocableHandlerMethodDecorator;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * @author taccisum - liaojinfeng@deepexi.com
 * @since 2020-02-20
 */
public class AmqpSupportAutoConfiguration implements BeanFactoryAware {
    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Bean
    public RabbitListenerConfigurer rabbitListenerConfigurer(List<InvocableHandlerMethodDecorator> decorators) {
        return registrar -> {
            DecoratedMessageHandlerMethodFactory factory = new DecoratedMessageHandlerMethodFactory(decorators);
            factory.setBeanFactory(beanFactory);
            factory.afterPropertiesSet();
            registrar.setMessageHandlerMethodFactory(factory);
        };
    }
}
