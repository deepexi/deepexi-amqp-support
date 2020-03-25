package com.deepexi.support.amqp.event;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;

/**
 * @author Y.H.Zhou - zhouyuhang@deepexi.com
 * @since 2020/3/25.
 * <p></p>
 */
public class PlaceHolderExchangeResolver implements ExchangeResolver, EnvironmentAware {
    private Environment environment;

    @Override
    public String resolve(String exchange) {
        Assert.notNull(environment, "Environment is Null.");
        return environment.resolveRequiredPlaceholders(exchange);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
