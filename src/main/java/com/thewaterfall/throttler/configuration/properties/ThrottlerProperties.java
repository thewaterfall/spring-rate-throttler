package com.thewaterfall.throttler.configuration.properties;

import org.springframework.beans.factory.annotation.Value;

public class ThrottlerProperties {
    @Value("${throttler.global.enabled:false}")
    private boolean enabled;

    @Value("${throttler.global.capacity:1}")
    private Integer capacity;

    @Value("${throttler.global.refill:1}")
    private Integer refill;

    @Value("${throttler.global.refill-period:1}")
    private Integer refillPeriod;

    @Value("${throttler.global.refill-period-unit:SECONDS}")
    private String refillPeriodUnit;

    @Value("${throttler.global.key-type:IP_ADDRESS}")
    private String keyType;

    @Value("${throttler.global.key-source:'}")
    private String keySource;

    public boolean isEnabled() {
        return enabled;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Integer getRefill() {
        return refill;
    }

    public Integer getRefillPeriod() {
        return refillPeriod;
    }

    public String getRefillPeriodUnit() {
        return refillPeriodUnit;
    }

    public String getKeyType() {
        return keyType;
    }

    public String getKeySource() {
        return keySource;
    }
}
