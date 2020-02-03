package com.deepexi.support.amqp.listener;

import com.deepexi.support.amqp.listener.handler.MessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;

import java.util.Map;

/**
 * @author Y.H.Zhou
 * @since 2020/2/3.
 * <p></p>
 */
@Slf4j
public abstract class AbstractListener {
    @Autowired
    private MessageHandler messageHandler;
    @Autowired
    private MessageHelper messageHelper;

    @RabbitHandler(isDefault = true)
    public final void listen(Object message, @Headers Map headers) {
        String messageId = messageHelper.getMessageId(headers);
        if (messageHelper.isConsumed(messageId)) {
            log.warn("message[message id: {}] has been consumed. it will be ignored.", messageId);
            return;
        }

        messageHandler.defaultListenerHandle(messageHelper.messageBuilder(headers, message));
    }

    public final void consume(Map headers, Object data, Action action) {
        String messageId = messageHelper.getMessageId(headers);
        if (messageHelper.isConsumed(messageId)) {
            log.warn("message[message id: {}] has been consumed. it will be ignored.", messageId);
            return;
        }

        Message message = messageHelper.messageBuilder(headers, data);
        try {
            messageHandler.preHandle(message);
            action.exec();
            messageHandler.postHandle(message);

            messageHandler.consumeAsSuccess(message);
        } catch (Exception e) {
            messageHandler.consumeAsFailure(e, message);
        }
    }
}
