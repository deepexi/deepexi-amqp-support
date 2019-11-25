package com.deepexi.support.amqp.event;

import com.deepexi.support.amqp.event.exception.EventMessageException;
import com.deepexi.support.amqp.event.util.EventMessageUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author taccisum - liaojinfeng@deepexi.com
 * @since 2019-11-25
 */
public class EventMessageTypeMapping {
    Map<String, Map<String, Class<?>>> code2clazzMapping;

    public EventMessageTypeMapping() {
        code2clazzMapping = new HashMap<>();
    }

    /**
     * @throws EventMessageException exists mapping
     */
    public void addMapping(String exchange, String event, Class<?> clazz) {
        Map<String, Class<?>> code2clazz = code2clazzMapping.computeIfAbsent(exchange, key -> new HashMap<>());
        if (code2clazz.get(event) != null) {
            throw new EventMessageException(String.format("Mapping of exchange %s and code %s is exists.", exchange, event));
        }
        code2clazz.put(event, clazz);
    }

    public Class<?> getMapping(String exchange, String event) {
        Map<String, Class<?>> map = code2clazzMapping.get(exchange);
        if (map == null) {
            return null;
        } else {
            return map.get(event);
        }
    }

    @Slf4j
    public static class Builder {
        private String pkg;
        private boolean strict = true;

        public Builder pkg(String pkg) {
            this.pkg = pkg;
            return this;
        }

        public Builder strict(boolean strict) {
            this.strict = strict;
            return this;
        }

        public EventMessageTypeMapping build() {
            Objects.requireNonNull(pkg, "should call pkg() first.");

            EventMessageTypeMapping mapping = new EventMessageTypeMapping();

            Set<Class<?>> classes = new EventMessageScanner(pkg).scan();
            for (Class<?> clazz : classes) {
                EventMessage annotation = EventMessageUtils.findAnnotation(clazz);
                if (annotation == null) {
                    if (strict) {
                        throw new EventMessageException(String.format("Should add annotation %s to class %s.", EventMessage.class, clazz));
                    } else {
                        log.warn("Class {} without annotation {} will be ignored.", clazz, EventMessage.class);
                    }
                } else {
                    mapping.addMapping(annotation.exchange(), annotation.code(), clazz);
                }
            }

            return mapping;
        }
    }
}
