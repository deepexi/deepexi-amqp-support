package com.deepexi.support.amqp.listener;

import com.deepexi.support.amqp.listener.extension.DefaultExtensionMessageHandlerMethodFactory;
import com.deepexi.support.amqp.listener.handler.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

import java.util.Objects;

/**
 * @author Y.H.Zhou
 * @since 2020/2/4.
 * <p></p>
 */
@Configuration
public class AmqpSupportConfig implements RabbitListenerConfigurer {

    @Autowired(required = false)
    private ListenerExtensionHandler listenerExtensionHandler;

    @Autowired
    private BeanFactory beanFactory;

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        if (Objects.isNull(listenerExtensionHandler)) {
            return;
        }

        registrar.setMessageHandlerMethodFactory(createMessageHandlerMethodFactory());
    }

    private MessageHandlerMethodFactory createMessageHandlerMethodFactory() {
        DefaultExtensionMessageHandlerMethodFactory handlerMethodFactory = new DefaultExtensionMessageHandlerMethodFactory(listenerExtensionHandler);
        handlerMethodFactory.setBeanFactory(beanFactory);
        handlerMethodFactory.afterPropertiesSet();
        return handlerMethodFactory;
    }
}
