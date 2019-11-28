package com.deepexi.support.amqp.event;

import com.deepexi.support.amqp.event.exception.EventMessageException;
import com.deepexi.support.amqp.event.util.EventMessageUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @author taccisum - liaojinfeng@deepexi.com
 * @since 2019-11-25
 */
public class EventMessageTypeMapping {
    Map<String, Map<String, Class<?>>> code2clazzMapping;
    private Class<?> defaultMappingClass;

    public EventMessageTypeMapping() {
        code2clazzMapping = new HashMap<>();
    }

    public void setDefaultMappingClass(Class<?> defaultMappingClass) {
        this.defaultMappingClass = defaultMappingClass;
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
        Class<?> clazz = null;
        if (map != null) {
            clazz = map.get(event);
        }
        return clazz != null ? clazz : def();
    }

    private Class<?> def() {
        return defaultMappingClass;
    }

    /**
     * TODO:: unit test
     */
    @Slf4j
    public static class Builder {
        private List<String> pkgs = new ArrayList<>();
        private boolean strict = true;
        private Class<?> def;

        public Builder def(Class<?> def) {
            this.def = def;
            return this;
        }

        public Builder pkg(String pkg) {
            this.pkgs.add(pkg);
            return this;
        }

        public Builder pkgs(String... pkgs) {
            this.pkgs.addAll(Arrays.asList(pkgs));
            return this;
        }

        public Builder strict(boolean strict) {
            this.strict = strict;
            return this;
        }

        public EventMessageTypeMapping build() {
            EventMessageTypeMapping mapping = new EventMessageTypeMapping();

            if (this.def != null) {
                mapping.setDefaultMappingClass(def);
            }

            Set<Class<?>> classes = new HashSet<>();
            for (String pkg : pkgs) {
                classes.addAll(new EventMessageScanner(pkg).scan());
            }

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
