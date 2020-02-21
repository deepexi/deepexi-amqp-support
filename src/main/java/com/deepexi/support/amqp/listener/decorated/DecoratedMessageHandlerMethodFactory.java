package com.deepexi.support.amqp.listener.decorated;

import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.invocation.InvocableHandlerMethod;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;

/**
 * TODO:: unit tests
 *
 * @author Y.H.Zhou
 * @author taccisum
 * @since 2020/2/5.
 */
public class DecoratedMessageHandlerMethodFactory extends DefaultMessageHandlerMethodFactory {
    private List<InvocableHandlerMethodDecorator> decorators;

    public DecoratedMessageHandlerMethodFactory(List<InvocableHandlerMethodDecorator> decorators) {
        this.decorators = decorators;
        this.decorators.sort(Comparator.comparingInt(InvocableHandlerMethodDecorator::getOrder).reversed());
    }

    @Override
    public InvocableHandlerMethod createInvocableHandlerMethod(Object bean, Method method) {
        return this.decorate(super.createInvocableHandlerMethod(bean, method));
    }

    InvocableHandlerMethod decorate(InvocableHandlerMethod invocableHandlerMethod) {
        InvocableHandlerMethod decorated = invocableHandlerMethod;
        for (InvocableHandlerMethodDecorator decorator : this.decorators) {
            decorated = decorator.decorate(decorated);
        }
        return decorated;
    }
}
