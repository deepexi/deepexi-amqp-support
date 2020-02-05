package com.deepexi.support.amqp.listener;

import org.springframework.messaging.MessageHeaders;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author Y.H.Zhou
 * @since 2020/2/3.
 * <p></p>
 */
public class Message {
    private String messageId;
    private MessageHeaders messageHeaders;
    private Object data;

    private Message() {

    }

    public boolean isConsumed() {
        return false;
    }

    public String getMessageId() {
        return messageId;
    }

    public MessageHeaders getHeaders() {
        return this.messageHeaders;
    }

    public void setHeaders(Map<String, Object> headers) {
        this.messageHeaders = new MessageHeaders(headers);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    private void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public static Builder builder(String messageId) {
        Assert.hasText(messageId, "message id can not be null.");

        Message message = new Message();
        message.setMessageId(messageId);

        return new Builder(message);
    }

    public static class Builder {
        private Message message;

        private Builder(Message message) {
            this.message = message;
        }

        public Builder headers(Map<String, Object> headers) {
            this.message.setHeaders(headers);
            return this;
        }

        public Builder messageData(Object messageData) {
            this.message.setData(messageData);
            return this;
        }

        public Message build() {
            return this.message;
        }
    }
}
