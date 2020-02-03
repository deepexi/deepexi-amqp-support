package com.deepexi.support.amqp.listener.handler;

import com.deepexi.support.amqp.listener.MessageHelper;
import com.deepexi.support.amqp.listener.Message;
import org.springframework.amqp.rabbit.listener.RabbitListenerErrorHandler;
import org.springframework.amqp.rabbit.listener.exception.ListenerExecutionFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Y.H.Zhou
 * @since 2020/2/3.
 * <p></p>
 */
@Component
public final class RabbitErrorHandler implements RabbitListenerErrorHandler {

    @Autowired
    private MessageHandler messageHandler;

    @Autowired
    private MessageHelper messageHelper;

    @Override
    public Object handleError(org.springframework.amqp.core.Message amqpMessage, org.springframework.messaging.Message<?> message, ListenerExecutionFailedException exception) {
        Message data = messageHelper.messageBuilder(message.getHeaders(), message.getPayload());
        data.setMessageId(amqpMessage.getMessageProperties().getMessageId());
        return messageHandler.listenAsFailure(data);
    }
}
