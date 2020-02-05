package com.deepexi.support.amqp.listener;

import com.deepexi.support.amqp.listener.handler.ListenerExtensionHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.invocation.InvocableHandlerMethod;
import org.springframework.util.Assert;

import java.lang.reflect.Method;

/**
 * @author Y.H.Zhou
 * @since 2020/2/5.
 * <p></p>
 */
public class InvocableExtensionHandlerMethod extends InvocableHandlerMethod {

    private ListenerExtensionHandler listenerExtensionHandler;

    public InvocableExtensionHandlerMethod(Object bean, Method method, ListenerExtensionHandler listenerExtensionHandler) {
        super(bean, method);
        Assert.notNull(listenerExtensionHandler, "ListenerExtensionHandler could not be null.");
        this.listenerExtensionHandler = listenerExtensionHandler;
    }

    @Override
    public Object invoke(Message<?> message, Object... providedArgs) throws Exception {
        if (listenerExtensionHandler.preHandle(message)) {
            return null;
        }

        Object result = super.invoke(message, providedArgs);
        listenerExtensionHandler.postHandle(message);
        return result;
    }
}
