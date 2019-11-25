package com.deepexi.support.amqp;

import com.deepexi.support.amqp.util.EventMessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author taccisum - liaojinfeng@deepexi.com
 * @since 2019-11-25
 */
@Slf4j
public class EventClassMapper implements ClassMapper {
    public static final String EVENT_CODE_HEADER = "_EVENT_CODE_";

    private ClassMapper delegate;
    private String eventCodeHeader;
    private EventMessageTypeMapping mapping;

    public EventClassMapper(String eventCodeHeader, EventMessageTypeMapping mapping) {
        Objects.requireNonNull(eventCodeHeader, "eventCodeHeader");
        Objects.requireNonNull(mapping, "mapping");
        this.eventCodeHeader = eventCodeHeader;
        this.mapping = mapping;
        this.delegate = new DefaultJackson2JavaTypeMapper();
    }

    public EventClassMapper(EventMessageTypeMapping mapping) {
        this(EVENT_CODE_HEADER, mapping);
    }

    public void setDelegateMapper(ClassMapper delegate) {
        this.delegate = delegate;
    }

    @Override
    public void fromClass(Class<?> clazz, MessageProperties messageProperties) {
        String event = EventMessageUtils.getEventCode(clazz);
        if (StringUtils.isEmpty(event)) {
            log.debug("class {} is not an event message. fallback to delegate {}.", clazz, this.delegate);
            this.delegate.fromClass(clazz, messageProperties);
        } else {
            messageProperties.getHeaders().put(this.eventCodeHeader, event);
        }
    }

    @Override
    public Class<?> toClass(MessageProperties messageProperties) {
        String exchange = messageProperties.getReceivedExchange();
        String event = (String) messageProperties.getHeaders().get(this.eventCodeHeader);
        if (StringUtils.isEmpty(event)) {
            log.debug("event code not found. fallback to delegate {}.", this.delegate);
            return this.delegate.toClass(messageProperties);
        } else {
            return this.mapping.getMapping(exchange, event);
        }
    }
}
