package com.deepexi.support.amqp.listener;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.invocation.InvocableHandlerMethod;
import org.springframework.util.Assert;

import java.lang.reflect.Method;

/**
 * @author Y.H.Zhou
 * @since 2020/2/5.
 * <p></p>
 */
public class SimpleMessageHandlerFactory extends DefaultMessageHandlerMethodFactory  {

    private MessageHandler messageHandler;

    public SimpleMessageHandlerFactory(MessageHandler messageHandler, BeanFactory beanFactory) {
        Assert.notNull(messageHandler, "MessageHandler");
        this.messageHandler = messageHandler;
        super.setBeanFactory(beanFactory);
        super.afterPropertiesSet();
    }

    @Override
    public InvocableHandlerMethod createInvocableHandlerMethod(Object bean, Method method) {
        InvocableHandlerMethod delegate = super.createInvocableHandlerMethod(bean, method);
        return new DelegatingInvocableHandleMethod(delegate, messageHandler);
    }
}
