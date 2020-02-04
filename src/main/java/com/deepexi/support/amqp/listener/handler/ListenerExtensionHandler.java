package com.deepexi.support.amqp.listener.handler;

/**
 * @author Y.H.Zhou
 * @since 2020/2/4.
 * <p></p>
 */
public interface ListenerExtensionHandler {
    default void preHandle() {}

    default void postHandle() {}
}
