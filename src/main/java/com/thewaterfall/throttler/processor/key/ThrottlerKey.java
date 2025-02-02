package com.thewaterfall.throttler.processor.key;

import java.util.Objects;

/**
 * Represents a key used for throttling requests.
 */
public class ThrottlerKey {
    private String methodSignature;
    private ThrottlerKeyType keyType;
    private String keyValue;

    public ThrottlerKey(String methodSignature, ThrottlerKeyType keyType, String keyValue) {
        this.methodSignature = methodSignature;
        this.keyType = keyType;
        this.keyValue = keyValue;
    }

    public String getMethodSignature() {
        return methodSignature;
    }

    public void setMethodSignature(String methodSignature) {
        this.methodSignature = methodSignature;
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ThrottlerKey that = (ThrottlerKey) o;
        return Objects.equals(methodSignature, that.methodSignature) &&
            Objects.equals(keyValue, that.keyValue) &&
            keyType == that.keyType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(methodSignature, keyType, keyValue);
    }

    @Override
    public String toString() {
        return "ThrottlerKey{" +
            "method=" + methodSignature +
            ", keyType=" + keyType +
            ", keyValue='" + keyValue + '\'' +
            '}';
    }
}
