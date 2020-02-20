package com.deepexi.support.amqp.listener;

import org.springframework.messaging.Message;
import org.springframework.messaging.handler.invocation.InvocableHandlerMethod;

/**
 * enhance for {@link InvocableHandlerMethod}
 *
 * @author Y.H.Zhou
 * @author taccisum
 * @since 2020/2/5.
 */
public abstract class InvocableHandlerMethodDecoration extends InvocableHandlerMethod {
    private InvocableHandlerMethod delegate;

    public InvocableHandlerMethodDecoration(InvocableHandlerMethod delegate) {
        super(delegate.getBean(), delegate.getMethod());
        this.delegate = delegate;
    }

    @Override
    public Object invoke(Message<?> message, Object... providedArgs) throws Exception {
        return this.delegate.invoke(message, providedArgs);
    }
}
