package com.deepexi.support.amqp.listener;

/**
 * @author Y.H.Zhou
 * @since 2020/2/3.
 * <p></p>
 */
@FunctionalInterface
public interface Action {
    void exec();
}
