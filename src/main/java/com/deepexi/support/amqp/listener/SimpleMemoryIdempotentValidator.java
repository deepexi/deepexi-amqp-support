package com.deepexi.support.amqp.listener;

import java.util.HashSet;
import java.util.Set;

/**
 * @author taccisum - liaojinfeng@deepexi.com
 * @since 2020-02-21
 */
public class SimpleMemoryIdempotentValidator extends AbstractIdempotentValidator {
    private Set<Object> idSet = new HashSet<>();

    /**
     * @param skipIfMessageIdNull indicate whether skip validation if message id null
     */
    public SimpleMemoryIdempotentValidator(boolean skipIfMessageIdNull) {
        super(skipIfMessageIdNull);
    }

    @Override
    protected boolean doValid(Object id) {
        if (idSet.contains(id)) {
            return true;
        } else {
            idSet.add(id);
            return false;
        }
    }
}
