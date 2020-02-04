package com.deepexi.support.amqp.listener;

import com.deepexi.support.amqp.listener.handler.MessageHandler;
import com.deepexi.support.amqp.listener.handler.SimpleMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.messaging.handler.annotation.Headers;

import java.util.Map;
import java.util.Objects;

/**
 * @author Y.H.Zhou
 * @since 2020/2/3.
 * <p></p>
 */
@Slf4j
public abstract class AbstractListener {
    private MessageHandler messageHandler;

    public AbstractListener(MessageHandler messageHandler) {
        if (Objects.isNull(messageHandler)) {
            throw new IllegalArgumentException("MessageHandler must not be null.");
        }

        this.messageHandler = messageHandler;
    }

    public AbstractListener() {
        this.messageHandler = new SimpleMessageHandler();
    }

    @RabbitHandler(isDefault = true)
    public final void listen(Object messageData, @Headers Map headers) {
        Message message = Message.builder(MessageHelper.getMessageId(headers))
                .headers(headers)
                .messageData(messageData)
                .build();

        if (message.isConsumed()) {
            messageHandler.handleConsumedMessage(message);
            return;
        }

        messageHandler.handleDefaultListener(message);
    }

    protected final void process(Map headers, Object messageData, Action action) {
        Message message = Message.builder(MessageHelper.getMessageId(headers))
                .headers(headers)
                .messageData(messageData)
                .build();

        if (message.isConsumed()) {
            messageHandler.handleConsumedMessage(message);
            return;
        }

        messageHandler.preExec(message);
        try {
            action.exec();
            messageHandler.consumeAsSuccess(message);
        } catch (Exception e) {
            messageHandler.consumeAsFailure(e, message);
        }
        messageHandler.postExec(message);
    }
}