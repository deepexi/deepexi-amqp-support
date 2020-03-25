package com.deepexi.support.amqp.event;

import com.deepexi.support.amqp.App;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


/**
 * @author Y.H.Zhou - zhouyuhang@deepexi.com
 * @since 2020/3/24.
 * <p></p>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class PlaceHolderExchangeResolverTest {

    @Autowired
    Environment environment;

    @Test
    public void resolve() {
        PlaceHolderExchangeResolver resolver = new PlaceHolderExchangeResolver();
        resolver.setEnvironment(environment);
        assertThat(resolver.resolve("${test.val}")).isEqualTo("default");
        assertThatThrownBy(() -> resolver.resolve("${not.exist}")).isInstanceOf(IllegalArgumentException.class);
        assertThat(this.environment.resolveRequiredPlaceholders("test")).isEqualTo("test");
    }
}
