package com.deepexi.support.amqp.listener;

import org.springframework.core.Ordered;
import org.springframework.messaging.handler.invocation.InvocableHandlerMethod;

/**
 * @author taccisum - liaojinfeng@deepexi.com
 * @since 2020-02-20
 */
public interface InvocableHandlerMethodDecorator extends Ordered {
    InvocableHandlerMethod decorate(InvocableHandlerMethod invocableHandlerMethod);

    @Override
    default int getOrder() {
        return 0;
    }
}
