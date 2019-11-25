package com.deepexi.support.amqp.event.asset.sub;

import com.deepexi.support.amqp.event.EventMessage;

/**
 * @author taccisum - liaojinfeng@deepexi.com
 * @since 2019-11-25
 */
@EventMessage(exchange = "ex", code = "foo3")
public class Foo3 {
}
