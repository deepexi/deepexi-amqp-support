package com.deepexi.support.amqp;

import java.util.HashMap;
import java.util.Map;

/**
 * @author taccisum - liaojinfeng@deepexi.com
 * @since 2019-11-25
 */
public class EventMessageTypeMapping {
    private Map<String, Map<String, Class<?>>> code2clazzMapping;

    public EventMessageTypeMapping() {
        code2clazzMapping = new HashMap<>();
    }

    public void addMapping(String exchange, String event, Class<?> clazz) {
        Map<String, Class<?>> code2clazz = code2clazzMapping.computeIfAbsent(exchange, key -> new HashMap<>());
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
}
