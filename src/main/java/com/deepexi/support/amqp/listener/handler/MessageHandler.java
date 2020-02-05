package com.deepexi.support.amqp.listener.handler;

import com.deepexi.support.amqp.listener.Message;

/**
 * @author Y.H.Zhou
 * @since 2020/2/4.
 * <p></p>
 */
public interface MessageHandler {

    default void handleDefaultListener(Message message) {}

    /**
     * 消费成功回调
     * @param message
     */
    default void consumeAsSuccess(Message message) {}

    /**
     * 消费失败异常回调
     * @param e
     * @param message
     */
    default void consumeAsFailure(Exception e, Message message) {}
}
