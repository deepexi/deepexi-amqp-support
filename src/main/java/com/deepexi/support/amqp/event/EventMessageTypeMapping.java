package com.deepexi.support.amqp.event;

import com.deepexi.support.amqp.event.exception.EventMessageException;
import com.deepexi.support.amqp.event.util.EventMessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @author taccisum - liaojinfeng@deepexi.com
 * @since 2019-11-25
 */
@Slf4j
public class EventMessageTypeMapping implements EnvironmentAware, InitializingBean {
    Map<String, Map<String, Class<?>>> code2clazzMapping = new HashMap<>();
    private Class<?> defaultMappingClass;
    private Environment environment;
    private Set<Class<?>> classes = new HashSet<>();
    private boolean strict = true;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void afterPropertiesSet() {
        for (Class<?> clazz : classes) {
            EventMessage annotation = EventMessageUtils.findAnnotation(clazz);
            if (annotation == null) {
                if (strict) {
                    throw new EventMessageException(String.format("Should add annotation %s to class %s.", EventMessage.class, clazz));
                } else {
                    log.warn("Class {} without annotation {} will be ignored.", clazz, EventMessage.class);
                }
            } else {
                addMapping(resolvePlaceHolder(annotation.exchange()), annotation.code(), clazz);
            }
        }
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

    public void addClasses(Set<Class<?>> classes) {
        this.classes.addAll(classes);
    }

    private String resolvePlaceHolder(String placeHolder) {
        Assert.notNull(environment, "Environment not be initialized.");
        return environment.resolveRequiredPlaceholders(placeHolder);
    }

    private void setStrict(boolean strict) {
        this.strict = strict;
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
        private EventMessageTypeMapping mapping;

        public Builder() {
            this.mapping = new EventMessageTypeMapping();
        }

        public Builder def(Class<?> def) {
            mapping.setDefaultMappingClass(def);
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
            mapping.setStrict(strict);
            return this;
        }

        public EventMessageTypeMapping build() {
            for (String pkg : pkgs) {
                mapping.addClasses(new EventMessageScanner(pkg).scan());
            }

            return mapping;
        }
    }
}