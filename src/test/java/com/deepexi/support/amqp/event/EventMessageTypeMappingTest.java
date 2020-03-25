package com.deepexi.support.amqp.event;

import com.deepexi.support.amqp.App;
import com.deepexi.support.amqp.event.asset.Foo4;
import com.deepexi.support.amqp.event.exception.EventMessageException;
import com.sun.deploy.util.SystemUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import sun.plugin2.util.SystemUtil;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = App.class)
@RunWith(SpringRunner.class)
public class EventMessageTypeMappingTest {
    private EventMessageTypeMapping mapping;

    @Autowired
    private ExchangeResolver exchangeResolver;

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

    @Test
    public void builder() {
        assertThat(exchangeResolver).isNotNull();

        EventMessageTypeMapping typeMapping = new EventMessageTypeMapping.Builder()
                .pkg("com.deepexi.support.amqp.event.asset")
                .resolver(exchangeResolver)
                .build();

        assertThat(typeMapping.getMapping("default", "foo4")).isEqualTo(Foo4.class);
    }

    @Bean
    public ExchangeResolver exchangeResolver() {
        return new PlaceHolderExchangeResolver();
    }
}