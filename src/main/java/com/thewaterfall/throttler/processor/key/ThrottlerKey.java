package com.thewaterfall.throttler.processor.key;

import java.lang.reflect.Method;

/**
 * Represents a key used for throttling requests.
 */
public class ThrottlerKey {
    private Method method;

    private ThrottlerKeyType keyType;
    private String keyValue;

    public ThrottlerKey(Method method, ThrottlerKeyType keyType, String keyValue) {
        this.method = method;
        this.keyType = keyType;
        this.keyValue = keyValue;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public ThrottlerKeyType getKeyType() {
        return keyType;
    }

    public void setKeyType(ThrottlerKeyType keyType) {
        this.keyType = keyType;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    @Override
    public String toString() {
        return "ThrottlerKey{" +
            "method=" + method +
            ", keyType=" + keyType +
            ", keyValue='" + keyValue + '\'' +
            '}';
    }
}
