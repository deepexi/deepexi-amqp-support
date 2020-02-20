package com.deepexi.support.amqp.listener;

import org.springframework.messaging.Message;

/**
 * valid if message is handled repeatedly
 *
 * @author taccisum - liaojinfeng@deepexi.com
 * @since 2020-02-20
 */
public interface IdempotentValidator {
    void valid(Message<?> message);

    class Dummy implements IdempotentValidator {
        @Override
        public void valid(Message<?> message) {
            // do nothing
        }
    }
}
