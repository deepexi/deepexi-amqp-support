package com.deepexi.support.amqp.listener;

import org.springframework.messaging.Message;

/**
 * @author taccisum - liaojinfeng@deepexi.com
 * @since 2020-02-21
 */
public interface MessageRecorder {
    void record(Message<?> message, boolean success, Object result, Exception error);

    class Dummy implements MessageRecorder {
        @Override
        public void record(Message<?> message, boolean success, Object result, Exception error) {
            // do nothing
        }
    }
}
