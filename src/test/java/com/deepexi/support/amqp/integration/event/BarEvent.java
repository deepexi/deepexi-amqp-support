package com.deepexi.support.amqp.integration.event;

import com.deepexi.support.amqp.event.EventMessage;
import lombok.Data;
import lombok.ToString;

/**
 * @author taccisum - liaojinfeng@deepexi.com
 * @since 2020-02-21
 */
@Data
@ToString
@EventMessage(exchange = "test-ex", code = "bar")
public class BarEvent {
    private String greeting;
}
