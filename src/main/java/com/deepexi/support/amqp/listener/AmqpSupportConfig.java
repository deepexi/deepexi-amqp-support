package com.deepexi.support.amqp.listener;

import com.deepexi.support.amqp.listener.handler.ListenerExtensionHandler;
import com.deepexi.support.amqp.listener.handler.MessageHandler;
import com.deepexi.support.amqp.listener.handler.SimpleMessageHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Y.H.Zhou
 * @since 2020/2/4.
 * <p></p>
 */
@Configuration
public class AmqpSupportConfig {

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
            public void preHandle() {

            }

            @Override
            public void postHandle() {

            }
        };
    }
}
