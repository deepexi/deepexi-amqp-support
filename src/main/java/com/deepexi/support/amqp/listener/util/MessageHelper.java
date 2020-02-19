package com.deepexi.support.amqp.listener.util;

import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Objects;

/**
 * @author Y.H.Zhou
 * @since 2020/2/4.
 * <p></p>
 */
public abstract class MessageHelper {

    public static String getMessageId(Map headers) {
        Objects.requireNonNull(headers, "headers");
        Object messageId = headers.get(AmqpHeaders.MESSAGE_ID);
        Assert.notNull(messageId, "message id not found.");
        return messageId.toString();
    }
}
