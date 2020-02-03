package com.deepexi.support.amqp.listener.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Y.H.Zhou
 * @since 2020/2/3.
 * <p></p>
 */
@ConfigurationProperties("mq-listener-support")
@Data
public class MQListenerProperties {
    private boolean fake;

    public boolean isFake() {
        return fake;
    }

    public void setFake(boolean fake) {
        this.fake = fake;
    }
}
