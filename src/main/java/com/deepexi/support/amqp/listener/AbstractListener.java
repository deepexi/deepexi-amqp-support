package com.deepexi.support.amqp.listener;

import com.deepexi.support.amqp.listener.handler.MessageHandler;
import com.deepexi.support.amqp.listener.handler.SimpleMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.util.Assert;

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
        Assert.notNull(messageHandler, "MessageHandler must not be null.");
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

        messageHandler.handleDefaultListener(message);
    }

    protected final void process(Map headers, Object messageData, Action action) {
        Message message = Message.builder(MessageHelper.getMessageId(headers))
                .headers(headers)
                .messageData(messageData)
                .build();

        try {
            action.exec();
        } catch (Exception e) {
            messageHandler.consumeAsFailure(e, message);
        }

        messageHandler.consumeAsSuccess(message);
    }
}