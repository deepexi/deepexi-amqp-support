package com.deepexi.support.amqp.listener;

import com.deepexi.support.amqp.listener.model.MQListenerProperties;
import com.deepexi.support.amqp.listener.model.Message;
import com.github.taccisum.shiro.web.autoconfigure.stateless.support.StatelessToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author Y.H.Zhou
 * @since 2020/2/3.
 * <p></p>
 */
@Slf4j
public abstract class AbstractListener {
    @Autowired
    private MQListenerProperties properties;

    @Autowired
    private MessageCallbackHandler callbackHandler;

    @RabbitHandler(isDefault = true)
    public void listen(Object message, @Headers Map headers) {
        log.warn("handler not found. message[content: {}, headers: {}] will be ignored.", message, headers);
    }

    protected void withAuthAndRecord(Object data, Map headers, Action action) {
        String messageId = headers.get(AmqpHeaders.MESSAGE_ID).toString();
        if (StringUtils.isEmpty(messageId)) {
            throw new IllegalArgumentException("message id could not be null.");
        }

        String token = headers.get(AmqpHeaders.TOKEN).toString();
        if (StringUtils.isEmpty(token)) {
            throw new IllegalArgumentException("token could not be null.");
        }

        withAuthAndRecord(data, messageId, token, action);
    }

    protected void withAuthAndRecord(Object data, String messageId, String token, Action action) {
        if (StringUtils.isEmpty(messageId)) {
            throw new IllegalArgumentException("message id could not be null.");
        }

        Message message = new Message()
                .setMessageId(messageId)
                .setToken(token)
                .setData(data);

        try {
            login(token);

            action.consume();
            callbackHandler.handle(true, message);

            logout();
        } catch (Exception e) {
            callbackHandler.handleAsFailure(e, message);
        }
    }

    private void login(String token) {
        if (properties.isFake()) {
            return;
        }

        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            log.warn("subject is authenticated.");
        } else {
            SecurityUtils.getSubject().login(new StatelessToken(token));
        }
    }

    private void logout() {
        if (properties.isFake()) {
            return;
        }

        Subject subject = SecurityUtils.getSubject();
        if (subject != null && subject.isAuthenticated()) {
            subject.logout();
        }
    }
}
