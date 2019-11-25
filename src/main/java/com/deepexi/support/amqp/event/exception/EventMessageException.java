package com.deepexi.support.amqp.event.exception;

/**
 * @author taccisum - liaojinfeng@deepexi.com
 * @since 2019-11-25
 */
public class EventMessageException extends RuntimeException {
    public EventMessageException(String message) {
        super(message);
    }

    public EventMessageException(String message, Throwable cause) {
        super(message, cause);
    }
}
