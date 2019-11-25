package com.deepexi.support.amqp.spring;

import com.deepexi.support.amqp.EventMessageScanner;
import com.deepexi.support.amqp.spring.asset.Foo1;
import com.deepexi.support.amqp.spring.asset.Foo2;
import com.deepexi.support.amqp.spring.asset.exclude.Bar1;
import com.deepexi.support.amqp.spring.asset.inner.Inner;
import com.deepexi.support.amqp.spring.asset.sub.Foo3;
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