package com.deepexi.support.amqp.event;

/**
 * @author Y.H.Zhou - zhouyuhang@deepexi.com
 * @since 2020/3/25.
 * <p></p>
 */
public interface ExchangeResolver {
    String resolve(String exchange);
}
