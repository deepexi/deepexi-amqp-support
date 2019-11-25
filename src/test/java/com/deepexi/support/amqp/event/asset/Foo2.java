package com.deepexi.support.amqp.event.asset;

import com.deepexi.support.amqp.event.EventMessage;

/**
 * @author taccisum - liaojinfeng@deepexi.com
 * @since 2019-11-25
 */
@EventMessage(exchange = "ex", code = "foo2")
public class Foo2 {
}
