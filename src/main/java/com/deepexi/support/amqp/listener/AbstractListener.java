package com.deepexi.support.amqp.listener;

import com.deepexi.support.amqp.listener.handler.MessageHandler;
import com.deepexi.support.amqp.listener.handler.SimpleMessageHandler;
import com.deepexi.support.amqp.listener.util.MessageHelper;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;

/**
 * @author Y.H.Zhou
 * @since 2020/2/3.
 * <p></p>
 */
@Slf4j
public abstract class AbstractListener {

    protected final void processMessage(Map headers, Object messageData, Action action) {
        processMessage(headers, messageData, action, new SimpleMessageHandler());
    }

    protected final void processMessage(Map headers, Object messageData, Action action, MessageHandler messageHandler) {
        Objects.requireNonNull(messageHandler, "messageHandler");
        Message message = Message.builder(MessageHelper.getMessageId(headers))
                .headers(headers)
                .messageData(messageData)
                .build();

        try {
            action.exec();
        } catch (Exception e) {
            messageHandler.consumeAsFailure(e, message);
            return;
        }

        messageHandler.consumeAsSuccess(message);
    }
}