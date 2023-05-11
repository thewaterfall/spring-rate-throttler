package com.thewaterfall.throttler.cache;

import java.util.concurrent.TimeUnit;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.thewaterfall.throttler.processor.key.ThrottlerKey;
import jakarta.annotation.PostConstruct;
import org.isomorphism.util.TokenBucket;
import org.springframework.beans.factory.annotation.Value;

public class ThrottlerInMemoryCache {
    private Cache<ThrottlerKey, TokenBucket> cache;

    @Value("${throttler.cache.in-memory.expire-after-access-seconds:3600}")
    private int expireAfterWriteSeconds;

    @Value("${throttler.cache.in-memory.max-size:100000}")
    private int maxSize;

    @PostConstruct
    public void init() {
        cache = Caffeine.newBuilder()
            .expireAfterAccess(expireAfterWriteSeconds, TimeUnit.SECONDS)
            .maximumSize(maxSize)
            .build();
    }

    public Cache<ThrottlerKey, TokenBucket> getCache() {
        return cache;
    }
}
