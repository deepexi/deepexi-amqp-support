package com.deepexi.support.amqp.listener.handler;

import com.deepexi.support.amqp.listener.Message;

/**
 * @author Y.H.Zhou
 * @since 2020/2/5.
 * <p></p>
 */
public interface ListenerErrorHandler {
    /**
     * 监听器异常回调
     * @param message
     * @return
     */
    default Object listenAsFailure(Message message) {
        return null;
    }
}
