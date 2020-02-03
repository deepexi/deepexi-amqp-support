package com.deepexi.support.amqp.listener;

import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Objects;

/**
 * @author Y.H.Zhou
 * @since 2020/2/3.
 * <p></p>
 */
public class MessageHelper {
    public boolean isConsumed(String messageId) {
        return false;
    }

    public final String getMessageId(Map headers) {
        Object messageId = headers.get(AmqpHeaders.MESSAGE_ID);
        if (Objects.isNull(messageId)) {
            throw new IllegalArgumentException("message id could not be null.");
        }

        String s = messageId.toString();
        if (StringUtils.isEmpty(s)) {
            throw new IllegalArgumentException("message id could not be empty.");
        }

        return s;
    }

    public final Message messageBuilder(Map headers, Object data) {
        Message message = new Message();

        message.setHeaders(headers);
        message.setData(data);
        message.setMessageId(getMessageId(headers));

        return message;
    }
}
