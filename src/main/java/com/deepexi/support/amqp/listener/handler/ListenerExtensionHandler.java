package com.deepexi.support.amqp.listener.handler;

import org.springframework.messaging.Message;

/**
 * @author Y.H.Zhou
 * @since 2020/2/4.
 * <p></p>
 */
public interface ListenerExtensionHandler {

    /**
     * @param message
     * @return 是否继续消费
     */
    default boolean preHandle(Message message) {
        return true;
    }

    default void postHandle(Message message) {}
}
