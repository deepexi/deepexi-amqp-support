package com.deepexi.support.amqp.listener;

import com.deepexi.support.amqp.listener.handler.DefaultMessageCallbackHandler;
import com.deepexi.support.amqp.listener.model.MQListenerProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Y.H.Zhou
 * @since 2020/2/3.
 * <p></p>
 */
@Configuration
@EnableConfigurationProperties({MQListenerProperties.class})
public class AmqpListenerConfig {

    @ConditionalOnMissingBean(MessageCallbackHandler.class)
    @Bean
    public MessageCallbackHandler getMessageCallbackHandler() {
        return new DefaultMessageCallbackHandler();
    }
}
