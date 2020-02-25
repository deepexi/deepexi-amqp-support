package com.deepexi.support.amqp.listener;

import com.deepexi.support.amqp.listener.decorator.AbstractIdempotentValidator;
import com.deepexi.support.amqp.listener.decorator.IdempotentValidator;
import com.deepexi.support.amqp.listener.decorator.SimpleMemoryIdempotentValidator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Y.H.Zhou
 * @since 2020/2/25.
 * <p></p>
 */
@Slf4j
public class IdempotentValidatorTest {
    private IdempotentValidator validator;
    private SimpleMemoryIdempotentValidator validator0;
    private Message message;

    @Before
    public void setup() {
        this.validator = new AbstractIdempotentValidator(false) {
            @Override
            protected boolean doValid(Object id) {
                // check message whether to repeat
                return false;
            }
        };

        this.validator0 = new SimpleMemoryIdempotentValidator(true);

        Map<String, String> headers = new HashMap<>(2);
        headers.put(AmqpHeaders.MESSAGE_ID, "message_id");
        this.message = new GenericMessage("payload", headers);
    }

    @Test
    public void doRepeatVaild() {
        assertThat(validator.isRepeated(message)).isEqualTo(false);
        assertThat(validator0.isRepeated(message)).isEqualTo(false);
        assertThat(validator0.isRepeated(message)).isEqualTo(true);
    }
}