package com.deepexi.support.amqp.listener;

import org.springframework.messaging.Message;

/**
 * @author Y.H.Zhou
 * @since 2020/2/4.
 * <p></p>
 */
public interface MessageHandler {

    default boolean preHandle(Message message) {
        return true;
    }

    default void handleError(Exception e, Message message) {}

    default void postHandle(Message message) {}
}
