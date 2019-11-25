package com.deepexi.support.amqp.event;

import java.lang.annotation.*;

/**
 * @author taccisum - liaojinfeng@deepexi.com
 * @since 2019-11-25
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EventMessage {
    String exchange();

    String code();
}
