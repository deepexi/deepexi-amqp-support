package com.deepexi.support.amqp.listener;

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
