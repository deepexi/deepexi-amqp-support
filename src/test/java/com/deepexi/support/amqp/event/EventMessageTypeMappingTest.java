package com.deepexi.support.amqp.event;

import com.deepexi.support.amqp.event.exception.EventMessageException;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

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

    @Test(expected = EventMessageException.class)
    public void addRepeatedMapping() {
        mapping.addMapping("ex", "REPEATED_EVENT", Integer.class);
        mapping.addMapping("ex", "REPEATED_EVENT", Integer.class);
    }

    @Test
    public void getMapping() {
        assertThat(mapping.getMapping("ex", "E1")).isEqualTo(String.class);
        assertThat(mapping.getMapping("ex", "E2")).isEqualTo(Integer.class);
    }

    @Test
    public void getMappingOnNull() {
        mapping.setDefaultMappingClass(Map.class);
        assertThat(mapping.getMapping("none", "none")).isEqualTo(Map.class);
    }
}