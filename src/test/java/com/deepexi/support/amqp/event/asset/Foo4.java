package com.deepexi.support.amqp.event.asset;

import com.deepexi.support.amqp.event.EventMessage;

/**
 * @author Y.H.Zhou - zhouyuhang@deepexi.com
 * @since 2020/3/26.
 * <p></p>
 */
@EventMessage(exchange = "${test.val}", code = "foo4")
public class Foo4 {
}
