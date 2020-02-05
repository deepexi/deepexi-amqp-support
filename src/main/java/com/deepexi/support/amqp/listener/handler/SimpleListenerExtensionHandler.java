package com.deepexi.support.amqp.listener.handler;

import com.deepexi.support.amqp.listener.MessageHelper;
import com.deepexi.support.amqp.listener.MessageSupport;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.util.Assert;

/**
 * @author Y.H.Zhou
 * @since 2020/2/5.
 * <p></p>
 */
public class SimpleListenerExtensionHandler implements ListenerExtensionHandler {

    private MessageSupport messageSupport;

    public SimpleListenerExtensionHandler(MessageSupport messageSupport) {
        Assert.notNull(messageSupport, "MessageHandler could not be null.");
        this.messageSupport = messageSupport;
    }

    public SimpleListenerExtensionHandler() {
        this.messageSupport = new SimpleMessageSupport();
    }

    @Override
    public boolean preHandle(Message message) {
        MessageHeaders headers = message.getHeaders();

        com.deepexi.support.amqp.listener.Message message1 = com.deepexi.support.amqp.listener.Message.builder(MessageHelper.getMessageId(headers))
                .headers(headers)
                .messageData(message.getPayload())
                .build();

        if (messageSupport.isMessageConsumed(message1)) {
            messageSupport.handleConsumedMessage(message1);
            return false;
        }

        return true;
    }
}
