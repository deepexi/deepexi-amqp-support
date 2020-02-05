package com.deepexi.support.amqp.listener.extension;

import com.deepexi.support.amqp.listener.handler.ListenerExtensionHandler;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolverComposite;
import org.springframework.messaging.handler.invocation.InvocableHandlerMethod;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

/**
 * @author Y.H.Zhou
 * @since 2020/2/5.
 * <p></p>
 */
public class DefaultExtensionMessageHandlerMethodFactory extends DefaultMessageHandlerMethodFactory {

    private ListenerExtensionHandler listenerExtensionHandler;

    private final HandlerMethodArgumentResolverComposite argumentResolvers =
            new HandlerMethodArgumentResolverComposite();

    public DefaultExtensionMessageHandlerMethodFactory(ListenerExtensionHandler listenerExtensionHandler) {
        Objects.requireNonNull(listenerExtensionHandler, "listenerExtensionHandler");
        this.listenerExtensionHandler = listenerExtensionHandler;
    }

    @Override
    public void setArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        if (Objects.isNull(argumentResolvers)) {
            this.argumentResolvers.clear();
            return;
        }

        this.argumentResolvers.addResolvers(argumentResolvers);
    }

    @Override
    public InvocableHandlerMethod createInvocableHandlerMethod(Object bean, Method method) {
        InvocableExtensionHandlerMethod handlerMethod = new InvocableExtensionHandlerMethod(bean, method, listenerExtensionHandler);
        argumentResolvers.addResolvers(super.initArgumentResolvers());
        handlerMethod.setMessageMethodArgumentResolvers(argumentResolvers);
        return handlerMethod;
    }
}