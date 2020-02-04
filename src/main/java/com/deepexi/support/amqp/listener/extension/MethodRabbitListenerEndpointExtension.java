package com.deepexi.support.amqp.listener.extension;

import com.deepexi.support.amqp.listener.handler.ListenerExtensionHandler;
import org.springframework.amqp.rabbit.listener.MethodRabbitListenerEndpoint;
import org.springframework.amqp.rabbit.listener.adapter.HandlerAdapter;
import org.springframework.amqp.rabbit.listener.adapter.MessagingMessageListenerAdapter;
import org.springframework.messaging.handler.invocation.InvocableHandlerMethod;

/**
 * @author Y.H.Zhou
 * @since 2020/2/4.
 * <p></p>
 */
public class MethodRabbitListenerEndpointExtension extends MethodRabbitListenerEndpoint {

    public ListenerExtensionHandler listenerExtensionHandler;

    public MethodRabbitListenerEndpointExtension(ListenerExtensionHandler listenerExtensionHandler) {
        super();
        this.listenerExtensionHandler = listenerExtensionHandler;
    }

    /**
     * 替换HandlerAdapter
     * @param messageListener
     * @return
     */
    @Override
    protected HandlerAdapter configureListenerAdapter(MessagingMessageListenerAdapter messageListener) {
        InvocableHandlerMethod invocableHandlerMethod = getMessageHandlerMethodFactory().createInvocableHandlerMethod(getBean(), getMethod());
        return new HandlerAdapterExtension(invocableHandlerMethod, listenerExtensionHandler);
    }
}
