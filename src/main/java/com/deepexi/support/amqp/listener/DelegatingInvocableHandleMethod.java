package com.deepexi.support.amqp.listener;

import org.springframework.messaging.Message;
import org.springframework.messaging.handler.invocation.InvocableHandlerMethod;
import org.springframework.util.Assert;

/**
 * @author Y.H.Zhou
 * @since 2020/2/5.
 * <p></p>
 */
public class DelegatingInvocableHandleMethod extends InvocableHandlerMethod {
    private MessageHandler messageHandler;
    private InvocableHandlerMethod delegate;

    public DelegatingInvocableHandleMethod(InvocableHandlerMethod delegate, MessageHandler messageHandler) {
        super(delegate.getBean(), delegate.getMethod());
        Assert.notNull(messageHandler, "MessageHandler");
        this.messageHandler = messageHandler;
        this.delegate = delegate;
    }

    @Override
    public Object invoke(Message<?> message, Object... providedArgs) throws Exception {
        Object result = null;
        if (!messageHandler.preHandle(message)) {
            return null;
        }

        try {
            result = this.delegate.invoke(message, providedArgs);
        } catch (IllegalStateException e) {
            throw e;
        } catch (Exception e) {
            messageHandler.handleError(e, message);
        }

        messageHandler.postHandle(message);
        return result;
    }
}
