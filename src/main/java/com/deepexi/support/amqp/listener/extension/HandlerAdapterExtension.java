package com.deepexi.support.amqp.listener.extension;

import com.deepexi.support.amqp.listener.handler.ListenerExtensionHandler;
import org.springframework.amqp.rabbit.listener.adapter.HandlerAdapter;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.invocation.InvocableHandlerMethod;

/**
 * @author Y.H.Zhou
 * @since 2020/2/4.
 * <p></p>
 */
public class HandlerAdapterExtension extends HandlerAdapter {

    private ListenerExtensionHandler listenerExtensionHandler;

    public HandlerAdapterExtension(InvocableHandlerMethod invokerHandlerMethod, ListenerExtensionHandler listenerExtensionHandler) {
        super(invokerHandlerMethod);
        this.listenerExtensionHandler = listenerExtensionHandler;
    }

    @Override
    public Object invoke(Message<?> message, Object... providedArgs) throws Exception {
        listenerExtensionHandler.preHandle(message);
        Object result = super.invoke(message, providedArgs);
        listenerExtensionHandler.postHandle(message);
        return result;
    }
}
