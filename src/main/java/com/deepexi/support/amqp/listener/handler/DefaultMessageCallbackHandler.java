package com.deepexi.support.amqp.listener.handler;

import com.deepexi.support.amqp.listener.model.Message;
import com.deepexi.support.amqp.listener.MessageCallbackHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Y.H.Zhou
 * @since 2020/2/3.
 * <p></p>
 */
@Slf4j
public final class DefaultMessageCallbackHandler implements MessageCallbackHandler {
    @Override
    public void handle(boolean success, Message message) {
        log.info("message[{}] handled success.[{}]", message.getMessageId(), success);
    }

    @Override
    public Object handleAsFailure(Exception e, Message message) {
        log.warn("message[{}] handled failure.", message.getMessageId(), e);
        return null;
    }
}
