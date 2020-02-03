package com.deepexi.support.amqp.listener.handler;

import com.deepexi.support.amqp.listener.Message;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Y.H.Zhou
 * @since 2020/2/3.
 * <p></p>
 */
@Slf4j
public class MessageHandler {
    public void preHandle(Message message) {

    }

    public void postHandle(Message message) {

    }

    public void defaultListenerHandle(Message message) {
        log.warn("handler not found. message[content: {}, headers: {}] will be ignored.", message, message.getHeaders());
    }

    /**
     * 消费成功回调
     * @param message
     */
    public void consumeAsSuccess(Message message) {
        log.info("message[{}] consume succeed.", message.getMessageId());
    }

    /**
     * 消费失败异常回调
     * @param e
     * @param message
     */
    public void consumeAsFailure(Exception e, Message message) {
        log.warn("message[{}] consume failed.", message.getMessageId(), e);
    }

    /**
     * 监听器异常回调
     * @param message
     * @return
     */
    public Object listenAsFailure(Message message) {
        log.info("message[{}] process failed.", message.getMessageId());
        return null;
    }
}
