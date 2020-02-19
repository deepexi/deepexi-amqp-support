package com.deepexi.support.amqp.listener.processor;

import com.deepexi.support.amqp.listener.util.MessageHelper;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.core.MessagePostProcessor;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Y.H.Zhou
 * @since 2020/2/19.
 * <p></p>
 */
public class MessageReceivedProcessor implements MessagePostProcessor {

    private List<IMessageReceivedHandler> handlers = new ArrayList<>();

    public void addHandler(IMessageReceivedHandler handler) {
        Objects.requireNonNull(handler, "handler");
        this.handlers.add(handler);
    }

    public void addHandlers(List<IMessageReceivedHandler> handlers) {
        for (IMessageReceivedHandler handler : handlers) {
            this.addHandler(handler);
        }
    }

    @Override
    public Message<?> postProcessMessage(Message<?> message) {
        MessageHeaders headers = message.getHeaders();

        com.deepexi.support.amqp.listener.Message message1 = com.deepexi.support.amqp.listener.Message.builder(MessageHelper.getMessageId(headers))
                .headers(headers)
                .messageData(message.getPayload())
                .build();

        if (CollectionUtils.isEmpty(handlers)) {
            return message;
        }

        List<IMessageReceivedHandler> handlerList = new ArrayList<>(handlers)
                .stream().sorted(Comparator.comparing(IMessageReceivedHandler::order))
                .collect(Collectors.toList());

        for (IMessageReceivedHandler handler : handlerList) {
            if (!handler.handle(message1)) {
                return null;
            }
        }

        return message;
    }
}
