package com.deepexi.support.amqp.listener;

import lombok.Data;

import java.util.Map;

/**
 * @author Y.H.Zhou
 * @since 2020/2/3.
 * <p></p>
 */
@Data
public class Message {
    private String messageId;
    private Map headers;
    private Object data;
}
