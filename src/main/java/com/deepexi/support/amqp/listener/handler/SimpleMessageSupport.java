package com.deepexi.support.amqp.listener.handler;

import com.deepexi.support.amqp.listener.Message;
import com.deepexi.support.amqp.listener.MessageSupport;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Y.H.Zhou
 * @since 2020/2/5.
 * <p></p>
 */
@Slf4j
public class SimpleMessageSupport implements MessageSupport {

    @Override
    public void handleConsumedMessage(Message message) {
        log.warn("message[{}] has already been consumed.", message.getMessageId());
    }
}
