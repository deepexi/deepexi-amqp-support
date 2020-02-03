package com.deepexi.support.amqp.listener;

import com.deepexi.support.amqp.listener.model.Message;

/**
 * @author Y.H.Zhou
 * @since 2020/2/3.
 * <p></p>
 */
public interface MessageCallbackHandler {
    void handle(boolean success, Message message);

    Object handleAsFailure(Exception e, Message message);
}
