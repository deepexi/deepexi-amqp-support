package com.deepexi.support.amqp.event;

import org.reflections.Reflections;

import java.util.Objects;
import java.util.Set;

/**
 * @author taccisum - liaojinfeng@deepexi.com
 * @since 2019-11-25
 */
public class EventMessageScanner {
    private String pkg;

    public EventMessageScanner(String pkg) {
        Objects.requireNonNull(pkg, "pkg");
        this.pkg = pkg;
    }

    public Set<Class<?>> scan() {
        Reflections reflections = new Reflections(this.pkg);
        return reflections.getTypesAnnotatedWith(EventMessage.class);
    }
}
