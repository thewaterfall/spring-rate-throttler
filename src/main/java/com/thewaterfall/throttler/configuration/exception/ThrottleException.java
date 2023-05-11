package com.thewaterfall.throttler.configuration.exception;

import com.thewaterfall.throttler.processor.key.ThrottlerKey;

/**
 * An exception thrown when a throttling limit is exceeded.
 */
public class ThrottleException extends RuntimeException {

    /**
     * The time in nanoseconds until the throttling limit for the specific key will be reset.
     */
    private long retryAfterNanos;

    /**
     * The key for which the throttling limit was exceeded.
     */
    private ThrottlerKey throttleKey;

    /**
     * Constructs a new ThrottleException with the specified retryAfterNanos and message.
     *
     * @param throttleKey the ThrottlerKey representing the key for which throttling limit
     *                    was exceeded
     * @param retryAfterNanos the time in nanoseconds until the throttling limit will be reset
     * @param message the detail message
     */
    public ThrottleException(ThrottlerKey throttleKey, long retryAfterNanos, String message) {
        super(message);
        this.throttleKey = throttleKey;
        this.retryAfterNanos = retryAfterNanos;
    }

    public long getRetryAfterNanos() {
        return retryAfterNanos;
    }

    public void setRetryAfterNanos(long retryAfterNanos) {
        this.retryAfterNanos = retryAfterNanos;
    }

    public ThrottlerKey getThrottleKey() {
        return throttleKey;
    }

    public void setThrottleKey(ThrottlerKey throttleKey) {
        this.throttleKey = throttleKey;
    }
}
