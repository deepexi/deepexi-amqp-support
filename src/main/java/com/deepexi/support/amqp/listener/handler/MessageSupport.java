package com.deepexi.support.amqp.listener.handler;

import com.deepexi.support.amqp.listener.Message;

/**
 * @author Y.H.Zhou
 * @since 2020/2/5.
 * <p></p>
 */
public interface MessageSupport {
    default boolean isMessageConsumed(Message message) {
        return false;
    }

    void handleConsumedMessage(Message message);
}
