package com.deepexi.support.amqp.listener.processor;

import com.deepexi.support.amqp.listener.Message;

/**
 * @author Y.H.Zhou
 * @since 2020/2/5.
 * <p></p>
 */
public interface IMessageReceivedHandler {
    boolean handle(Message message);

    default Integer order() {
        return Integer.MAX_VALUE;
    }
}
