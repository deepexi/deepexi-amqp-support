package com.deepexi.support.amqp.listener.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Y.H.Zhou
 * @since 2020/2/3.
 * <p></p>
 */
@Data
@Accessors(chain = true)
public class Message {
    private String messageId;
    private String token;
    private Object data;
}
