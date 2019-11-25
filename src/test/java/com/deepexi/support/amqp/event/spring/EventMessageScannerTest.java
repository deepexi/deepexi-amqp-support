package com.deepexi.support.amqp.event.spring;

import com.deepexi.support.amqp.event.EventMessageScanner;
import com.deepexi.support.amqp.event.spring.asset.Foo1;
import com.deepexi.support.amqp.event.spring.asset.Foo2;
import com.deepexi.support.amqp.event.spring.asset.sub.Foo3;
import com.deepexi.support.amqp.event.spring.asset.exclude.Bar1;
import com.deepexi.support.amqp.event.spring.asset.inner.Inner;
import org.junit.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class EventMessageScannerTest {
    @Test
    public void scan() {
        Set<Class<?>> classes = new EventMessageScanner(this.getClass().getPackage().getName() + ".asset").scan();
        assertThat(classes.size()).isEqualTo(4);
        assertThat(classes).contains(Foo1.class, Foo2.class, Foo3.class, Inner.Foo.class);
        assertThat(classes).doesNotContain(Bar1.class);
    }
}