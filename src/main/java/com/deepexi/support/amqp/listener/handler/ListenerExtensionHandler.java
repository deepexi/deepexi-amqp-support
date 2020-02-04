package com.deepexi.support.amqp.listener.handler;

import org.springframework.messaging.Message;

/**
 * @author Y.H.Zhou
 * @since 2020/2/4.
 * <p></p>
 */
public interface ListenerExtensionHandler {
    default void preHandle(Message message) {}

    default void postHandle(Message message) {}
}
