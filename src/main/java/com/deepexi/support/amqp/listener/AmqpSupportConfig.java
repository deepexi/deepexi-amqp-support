package com.deepexi.support.amqp.listener;

import com.deepexi.support.amqp.listener.extension.RabbitConfigExtensionBeanFactoryPostProcessor;
import com.deepexi.support.amqp.listener.extension.RabbitListenerAnnotationBeanPostProcessorExtension;
import com.deepexi.support.amqp.listener.handler.ListenerExtensionHandler;
import com.deepexi.support.amqp.listener.handler.MessageHandler;
import com.deepexi.support.amqp.listener.handler.SimpleMessageHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListenerAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

/**
 * @author Y.H.Zhou
 * @since 2020/2/4.
 * <p></p>
 */
@Configuration
public class AmqpSupportConfig  {

    @Autowired
    private ListenerExtensionHandler listenerExtensionHandler;

    @ConditionalOnMissingBean({MessageHandler.class})
    @Bean
    public MessageHandler getMessageHandler() {
        return new SimpleMessageHandler();
    }

    @ConditionalOnMissingBean({ListenerExtensionHandler.class})
    @Bean
    public ListenerExtensionHandler getListenerExtensionHandler() {
        return new ListenerExtensionHandler() {
            @Override
            public void preHandle(Message message) {

            }

            @Override
            public void postHandle(Message message) {

            }
        };
    }

    @Bean
    public RabbitConfigExtensionBeanFactoryPostProcessor getRabbitConfigExtensionBeanFactory() {
        return new RabbitConfigExtensionBeanFactoryPostProcessor();
    }

    @Bean
    public RabbitListenerAnnotationBeanPostProcessor getRabbitListenerAnnotationBeanPostProcessor() {
        return new RabbitListenerAnnotationBeanPostProcessorExtension(listenerExtensionHandler);
    }
}
