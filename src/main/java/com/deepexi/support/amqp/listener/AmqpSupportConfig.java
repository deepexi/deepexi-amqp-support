package com.deepexi.support.amqp.listener;

import com.deepexi.support.amqp.listener.handler.ListenerExtensionHandler;
import com.deepexi.support.amqp.listener.handler.MessageHandler;
import com.deepexi.support.amqp.listener.handler.SimpleListenerExtensionHandler;
import com.deepexi.support.amqp.listener.handler.SimpleMessageHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

/**
 * @author Y.H.Zhou
 * @since 2020/2/4.
 * <p></p>
 */
@Configuration
public class AmqpSupportConfig implements RabbitListenerConfigurer {

    @Autowired
    private ListenerExtensionHandler listenerExtensionHandler;

    @Autowired
    private MessageHandler messageHandler;

    @Autowired
    private BeanFactory beanFactory;

    @ConditionalOnMissingBean({MessageHandler.class})
    @Bean
    public MessageHandler getMessageHandler() {
        return new SimpleMessageHandler();
    }

    @ConditionalOnMissingBean({ListenerExtensionHandler.class})
    @Bean
    public ListenerExtensionHandler getListenerExtensionHandler() {
        return new SimpleListenerExtensionHandler(messageHandler);
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(createMessageHandlerMethodFactory());
    }

    private MessageHandlerMethodFactory createMessageHandlerMethodFactory() {
        DefaultExtensionMessageHandlerMethodFactory handlerMethodFactory = new DefaultExtensionMessageHandlerMethodFactory(listenerExtensionHandler);
        handlerMethodFactory.setBeanFactory(beanFactory);
        handlerMethodFactory.afterPropertiesSet();
        return handlerMethodFactory;
    }
}
