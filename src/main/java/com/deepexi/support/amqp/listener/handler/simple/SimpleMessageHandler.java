package com.deepexi.support.amqp.listener.handler.simple;

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
    public void handleDefaultListener(Message message) {
        log.warn("handler not found. message[content: {}, headers: {}] will be ignored.", message, message.getHeaders());
    }

    @Override
    public void consumeAsSuccess(Message message) {
        log.info("message[{}] consume succeed.", message.getMessageId());
    }

    @Override
    public void consumeAsFailure(Exception e, Message message) {
        log.warn("message[{}] consume failed.", message.getMessageId(), e);
    }

    @Override
    public Object listenAsFailure(Message message) {
        log.info("message[{}] listener execute failed.", message.getMessageId());
        return null;
    }
}
