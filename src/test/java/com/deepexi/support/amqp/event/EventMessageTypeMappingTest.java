package com.deepexi.support.amqp.event;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EventMessageTypeMappingTest {
    private EventMessageTypeMapping mapping;

    @Before
    public void setUp() throws Exception {
        mapping = new EventMessageTypeMapping();
        mapping.addMapping("ex", "E1", String.class);
        mapping.addMapping("ex", "E2", Integer.class);
    }

    @Test
    public void addMapping() {
        assertThat(mapping.code2clazzMapping.get("ex")).isNotNull();
        assertThat(mapping.code2clazzMapping.get("ex").get("E1")).isEqualTo(String.class);
        assertThat(mapping.code2clazzMapping.get("ex").get("E2")).isEqualTo(Integer.class);
    }

    @Test
    public void getMapping() {
        assertThat(mapping.getMapping("ex", "E1")).isEqualTo(String.class);
        assertThat(mapping.getMapping("ex", "E2")).isEqualTo(Integer.class);
    }
}