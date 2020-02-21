package com.deepexi.support.amqp.listener.decorator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;

/**
 * @author taccisum - liaojinfeng@deepexi.com
 * @since 2020-02-21
 */
@Slf4j
public abstract class AbstractIdempotentValidator implements IdempotentValidator {
    private boolean skipIfMessageIdNull = true;

    public AbstractIdempotentValidator(boolean skipIfMessageIdNull) {
        this.skipIfMessageIdNull = skipIfMessageIdNull;
    }

    @Override
    public boolean isRepeated(Message<?> message) {
        Object id = message.getHeaders().get(AmqpHeaders.MESSAGE_ID);
        if (id == null && skipIfMessageIdNull) {
            return false;
        }
        boolean result = doValid(id);
        if (result) {
            log.warn("message[id: {}] is consumed repeatedly.", id);
        }
        return result;
    }

    protected abstract boolean doValid(Object id);
}
