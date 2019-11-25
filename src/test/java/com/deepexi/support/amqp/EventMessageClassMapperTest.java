package com.deepexi.support.amqp;

import org.junit.Before;
import org.junit.Test;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.ClassMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class EventMessageClassMapperTest {
    private EventMessageTypeMapping mapping;
    private EventMessageClassMapper mapper;
    private ClassMapper delegate;

    @Before
    public void setUp() throws Exception {
        mapping = mock(EventMessageTypeMapping.class);
        delegate = mock(ClassMapper.class);
        mapper = new EventMessageClassMapper(mapping);
        mapper.setDelegateMapper(delegate);
    }

    @Test
    public void defaultEventCode() {
        assertThat(mapper.eventCodeHeader).isEqualTo(EventMessageClassMapper.EVENT_CODE_HEADER);
    }

    @Test
    public void fromClass() {
        MessageProperties properties = new MessageProperties();
        mapper.fromClass(FooEventMessage.class, properties);
        assertThat(properties.getHeaders().get(mapper.eventCodeHeader)).isEqualTo("FOO");
    }

    @Test
    public void toClass() {
        doReturn(FooEventMessage.class).when(mapping).getMapping("ex", "FOO");
        MessageProperties properties = new MessageProperties();
        properties.setReceivedExchange("ex");
        properties.getHeaders().put(mapper.eventCodeHeader, "FOO");
        assertThat(mapper.toClass(properties)).isEqualTo(FooEventMessage.class);
    }

    @Test
    public void delegateFromClass() {
        doNothing().when(delegate).fromClass(any(), any());
        MessageProperties properties = new MessageProperties();
        mapper.fromClass(OtherClass.class, properties);
        verify(delegate, times(1)).fromClass(any(), any());
    }

    @Test
    public void delegateToClass() {
        doReturn(OtherClass.class).when(delegate).toClass(any());
        MessageProperties properties = new MessageProperties();
        assertThat(mapper.toClass(properties)).isEqualTo(OtherClass.class);
        verify(delegate, times(1)).toClass(any());
    }

    @EventMessage(exchange = "ex", code = "FOO")
    private static class FooEventMessage {
    }

    private static class OtherClass {
    }
}