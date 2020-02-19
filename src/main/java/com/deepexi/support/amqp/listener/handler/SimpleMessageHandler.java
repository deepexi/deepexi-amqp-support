package com.deepexi.support.amqp.listener.handler;

import com.deepexi.support.amqp.listener.Message;
import com.deepexi.support.amqp.listener.handler.MessageHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Y.H.Zhou
 * @since 2020/2/3.
 * <p></p>
 */
@Slf4j
public class SimpleMessageHandler implements MessageHandler {

    @Override
    public void consumeAsSuccess(Message message) {
        log.debug("message[{}] consume succeed.", message.getMessageId());
    }

    @Override
    public void consumeAsFailure(Exception e, Message message) {
        log.debug("message[{}] consume failed.", message.getMessageId(), e);
    }
}
