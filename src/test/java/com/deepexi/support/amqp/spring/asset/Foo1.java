package com.deepexi.support.amqp.spring.asset;

import com.deepexi.support.amqp.EventMessage;

/**
 * @author taccisum - liaojinfeng@deepexi.com
 * @since 2019-11-25
 */
@EventMessage(exchange = "ex", code = "foo1")
public class Foo1 {
}
