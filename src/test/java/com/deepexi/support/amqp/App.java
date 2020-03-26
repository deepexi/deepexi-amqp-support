package com.deepexi.support.amqp;

import com.deepexi.support.amqp.event.EventMessageTypeMapping;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author Y.H.Zhou - zhouyuhang@deepexi.com
 * @since 2020/3/25.
 * <p></p>
 */
@SpringBootApplication
public class App {

    @Bean
    public EventMessageTypeMapping eventMessageTypeMapping() {
        return new EventMessageTypeMapping.Builder()
                .pkg("com.deepexi.support.amqp.event.asset")
                .build();
    }
}
