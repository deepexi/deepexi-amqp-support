package com.deepexi.support.amqp.listener.handler.simple;

import com.deepexi.support.amqp.listener.Message;
import com.deepexi.support.amqp.listener.handler.ListenerErrorHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Y.H.Zhou
 * @since 2020/2/5.
 * <p></p>
 */
@Slf4j
public class SimpleListenerErrorHandler implements ListenerErrorHandler {

    @Override
    public Object listenAsFailure(Message message) {
        log.warn("message[{}] listener execute failed.", message.getMessageId());
        return null;
    }
}
