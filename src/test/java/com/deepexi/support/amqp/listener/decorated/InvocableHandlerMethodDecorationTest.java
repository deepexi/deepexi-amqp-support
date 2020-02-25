package com.deepexi.support.amqp.listener.decorated;

import org.junit.Test;
import org.springframework.messaging.handler.invocation.InvocableHandlerMethod;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InvocableHandlerMethodDecorationTest {
    @Test
    public void invoke() throws Exception {
        InvocableHandlerMethod delegate = mock(InvocableHandlerMethod.class);
        when(delegate.invoke(any(), any())).thenReturn("ok");
        when(delegate.getBean()).thenReturn(new Object());
        when(delegate.getMethod()).thenReturn(this.getClass().getMethod("invoke"));
        InvocableHandlerMethodDecoration decoration = new InvocableHandlerMethodDecoration(delegate) {
        };
        assertThat(decoration.invoke(null, null)).isEqualTo("ok");
    }
}