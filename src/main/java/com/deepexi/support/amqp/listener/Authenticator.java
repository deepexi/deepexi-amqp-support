package com.deepexi.support.amqp.listener;

import org.springframework.messaging.Message;

/**
 * do authenticate before consume message
 *
 * @author taccisum - liaojinfeng@deepexi.com
 * @since 2020-02-21
 */
public interface Authenticator {
    void login(Message<?> message);

    void logout(Message<?> message);

    class Dummy implements Authenticator {
        @Override
        public void login(Message<?> message) {
            // do nothing
        }

        @Override
        public void logout(Message<?> message) {
            // do nothing
        }
    }
}
