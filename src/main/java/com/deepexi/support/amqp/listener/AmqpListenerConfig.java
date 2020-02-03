package com.deepexi.support.amqp.listener;

import com.deepexi.support.amqp.listener.handler.MessageHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Y.H.Zhou
 * @since 2020/2/3.
 * <p></p>
 */
@Configuration
public class AmqpListenerConfig {

    @ConditionalOnMissingBean(MessageHelper.class)
    @Bean
    public MessageHelper getMessageHelper() {
        return new MessageHelper();
    }

    @ConditionalOnMissingBean(MessageHandler.class)
    @Bean
    public MessageHandler getMessageHandler() {
        return new MessageHandler();
    }
}
