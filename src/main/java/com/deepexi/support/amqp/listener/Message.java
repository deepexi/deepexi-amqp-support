package com.deepexi.support.amqp.listener;

import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author Y.H.Zhou
 * @since 2020/2/3.
 * <p></p>
 */
public class Message {
    private String messageId;
    private Map headers;
    private Object data;

    public boolean isConsumed() {
        return false;
    }

    private Message() {}

    public String getMessageId() {
        return messageId;
    }

    public Map getHeaders() {
        return headers;
    }

    public void setHeaders(Map headers) {
        this.headers = headers;
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
        if (StringUtils.isEmpty(messageId)) {
            throw new IllegalArgumentException("message id can not be null.");
        }

        Builder builder = new Builder();
        Message message = new Message();
        message.setMessageId(messageId);

        return builder;
    }

    public static class Builder {
        private Message message;

        public Builder headers(Map headers) {
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

        private Builder() {

        }
    }
}
