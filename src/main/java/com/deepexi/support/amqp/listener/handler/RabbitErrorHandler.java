package com.deepexi.support.amqp.listener.handler;

import com.deepexi.support.amqp.listener.model.Message;
import com.deepexi.support.amqp.listener.MessageCallbackHandler;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.amqp.rabbit.listener.RabbitListenerErrorHandler;
import org.springframework.amqp.rabbit.listener.exception.ListenerExecutionFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Y.H.Zhou
 * @since 2020/2/3.
 * <p></p>
 */
@Component
public class RabbitErrorHandler implements RabbitListenerErrorHandler {

    @Autowired
    private MessageCallbackHandler callbackHandler;

    @Override
    public Object handleError(org.springframework.amqp.core.Message amqpMessage, org.springframework.messaging.Message<?> message, ListenerExecutionFailedException exception) {
        Message data = new Message();
        data.setMessageId(amqpMessage.getMessageProperties().getMessageId());
        Object result = callbackHandler.handleAsFailure(exception, data);

        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout();
        }

        return result;
    }
}
