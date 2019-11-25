package com.deepexi.support.amqp.util;

import com.deepexi.support.amqp.EventMessage;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * @author taccisum - liaojinfeng@deepexi.com
 * @since 2019-11-25
 */
public abstract class EventMessageUtils {
    public static String getEventCode(Class<?> clazz) {
        return findEventMessageAnnotationOrThrow(clazz).code();
    }

    public static String getEventExchange(Class<?> clazz) {
        return findEventMessageAnnotationOrThrow(clazz).exchange();
    }

    static EventMessage findEventMessageAnnotationOrThrow(Class<?> clazz) {
        EventMessage annotation = AnnotationUtils.findAnnotation(clazz, EventMessage.class);
        if (annotation == null) {
            throw new UnsupportedOperationException(String.format("should add annotation %s to class %s first.", EventMessage.class, clazz));
        }
        return annotation;
    }
}
