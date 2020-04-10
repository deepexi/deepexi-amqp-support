package com.deepexi.support.amqp.integration.event;

import com.deepexi.support.amqp.event.EventMessage;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Y.H.Zhou - zhouyuhang@deepexi.com
 * @since 2020/3/27.
 * <p></p>
 */
@EventMessage(exchange = "${unit-test.exchange}", code = "Foo1")
@Getter
@ToString
public class Foo1Event {
    private String foo1 = "foo1";
    private String name = "name";
}
