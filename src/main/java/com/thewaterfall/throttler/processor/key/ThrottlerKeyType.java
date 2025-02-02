package com.thewaterfall.throttler.processor.key;

import com.thewaterfall.throttler.processor.ThrottlerProcessor;
import com.thewaterfall.throttler.processor.key.evaluator.impl.CookieThrottlerKeyEvaluator;
import com.thewaterfall.throttler.processor.key.evaluator.impl.HeaderThrottlerKeyEvaluator;
import com.thewaterfall.throttler.processor.key.evaluator.ThrottlerKeyEvaluator;
import com.thewaterfall.throttler.processor.key.evaluator.impl.IpAddressThrottlerKeyEvaluator;

/**
 * An enumeration representing the types of throttler keys that can be used by the
 * {@link ThrottlerProcessor}. Each key type has an associated {@link ThrottlerKeyEvaluator}
 * that is responsible for evaluating a key from a given request.
 */
public enum ThrottlerKeyType {
    /**
     * A throttler key type that uses an IP address of the requester as a key.
     */
    IP_ADDRESS(new IpAddressThrottlerKeyEvaluator()),

    /**
     * A throttler key type that uses a header of the requester as a key.
     */
    HEADER(new HeaderThrottlerKeyEvaluator()),

    /**
     * A throttler key type that uses a cookie of the requester as a key.
     */
    COOKIE(new CookieThrottlerKeyEvaluator());

    private final ThrottlerKeyEvaluator evaluator;

    ThrottlerKeyType(ThrottlerKeyEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    public ThrottlerKeyEvaluator getEvaluator() {
        return evaluator;
    }
}
