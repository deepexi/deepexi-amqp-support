package com.deepexi.support.amqp.listener;

import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;

/**
 * @author taccisum - liaojinfeng@deepexi.com
 * @since 2020-02-21
 */
public abstract class AbstractIdempotentValidator implements IdempotentValidator {
    private boolean skipIfMessageIdNull = true;

    public AbstractIdempotentValidator(boolean skipIfMessageIdNull) {
        this.skipIfMessageIdNull = skipIfMessageIdNull;
    }

    @Override
    public void valid(Message<?> message) {
        Object id = message.getHeaders().get(AmqpHeaders.MESSAGE_ID);
        if (id == null && skipIfMessageIdNull) {
            return;
        }
        doValid(id);
    }

    protected abstract void doValid(Object id);
}
