package com.deepexi.support.amqp.event.integration.event;

import com.deepexi.support.amqp.event.EventMessage;
import lombok.Data;
import lombok.ToString;

/**
 * @author taccisum - liaojinfeng@deepexi.com
 * @since 2019-11-25
 */
@Data
@ToString
@EventMessage(exchange = "test-ex", code = "foo")
public class FooEvent {
    private String str;
    private Integer num;
}
