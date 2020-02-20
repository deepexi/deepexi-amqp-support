package com.deepexi.support.amqp.listener;

import org.springframework.messaging.Message;

/**
 * valid if message is handled repeatedly
 *
 * @author taccisum - liaojinfeng@deepexi.com
 * @since 2020-02-20
 */
public interface IdempotentValidator {
    boolean isRepeated(Message<?> message);

    class Dummy implements IdempotentValidator {
        @Override
        public boolean isRepeated(Message<?> message) {
            // do nothing
            return false;
        }
    }
}
