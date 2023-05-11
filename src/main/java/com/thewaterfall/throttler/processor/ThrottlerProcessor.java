package com.thewaterfall.throttler.processor;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import com.thewaterfall.throttler.cache.ThrottlerInMemoryCache;
import com.thewaterfall.throttler.configuration.annotation.Throttle;
import com.thewaterfall.throttler.configuration.exception.ThrottleException;
import com.thewaterfall.throttler.configuration.properties.ThrottlerProperties;
import com.thewaterfall.throttler.processor.key.ThrottlerKey;
import com.thewaterfall.throttler.processor.key.ThrottlerKeyType;
import jakarta.servlet.http.HttpServletRequest;
import org.isomorphism.util.TokenBucket;
import org.isomorphism.util.TokenBuckets;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * The {@code ThrottlerProcessor} class is responsible for processing requests and throttling them
 * based on the annotation present on the method being called. It uses cache to keep track of
 * the number of request made and their rate of consumption, and a key processor to determine
 * the key used to identify the source or user of the request.
 */
public class ThrottlerProcessor {
    private final ThrottlerInMemoryCache cache;
    private final ThrottlerKeyProcessor keyProcessor;
    private final ThrottlerProperties properties;

    public ThrottlerProcessor(ThrottlerInMemoryCache cache,
                              ThrottlerKeyProcessor keyProcessor,
                              ThrottlerProperties properties) {
        this.cache = cache;
        this.keyProcessor = keyProcessor;
        this.properties = properties;
    }

    /**
     * Throttles the incoming request based on the annotation present on the method being called.
     * If neither tne method nor class has a throttle annotation (and global configuration is not
     * configured), the request is allowed to proceed. Otherwise, the request is checked against
     * a token bucket in the cache. If the bucket exists, the request is allowed to proceed if
     * there are enough tokens available. Otherwise, a {@code ThrottleException} is thrown.
     *
     * @param request The {@code HttpServletRequest} object representing the incoming request.
     * @param method The {@code Method} object representing the method being throttled.
     * @return {@code true} if the request was throttled successfully, {@code false} otherwise.
     * @throws ThrottleException if the request was not throttled successfully due to exceeding
     *                           the request limit.
     */
    public boolean throttle(HttpServletRequest request, Method method) throws ThrottleException {
        Optional<Throttle> throttle = findThrottleAnnotation(method);

        if (throttle.isEmpty()) {
            return true;
        }

        ThrottlerKey key = keyProcessor.process(request, method, throttle.get().key());
        TokenBucket bucket = cache.getCache().getIfPresent(key);

        if (Objects.isNull(bucket)) {
            bucket = TokenBuckets.builder()
                .withCapacity(throttle.get().capacity())
                .withFixedIntervalRefillStrategy(
                    throttle.get().refill(),
                    throttle.get().period(),
                    TimeUnit.of(throttle.get().unit()))
                .build();

            cache.getCache().put(key, bucket);
        }

        if (!bucket.tryConsume()) {
            throw new ThrottleException(key,
                bucket.getDurationUntilNextRefill(TimeUnit.NANOSECONDS), "Out of request limit");
        }

        return true;
    }

    private Optional<Throttle> findThrottleAnnotation(Method method) {
        Throttle throttle = null;

        // Method level
        throttle = AnnotationUtils.findAnnotation(method, Throttle.class);

        if (Objects.nonNull(throttle)) {
            return Optional.of(throttle);
        }

        // Class level
        throttle = AnnotationUtils.findAnnotation(method.getDeclaringClass(),
            Throttle.class);

        if (Objects.nonNull(throttle)) {
            return Optional.of(throttle);
        }

        // Global level
        return getGlobalLevelThrottle();
    }

    private Optional<Throttle> getGlobalLevelThrottle() {
        if (!properties.isEnabled()) {
            return Optional.empty();
        }

        return Optional.of(new Throttle() {
            @Override
            public int capacity() {
                return properties.getCapacity();
            }

            @Override
            public int refill() {
                return properties.getRefill();
            }

            public int period() {
                return properties.getRefillPeriod();
            }

            @Override
            public ChronoUnit unit() {
                return ChronoUnit.valueOf(properties.getRefillPeriodUnit());
            }

            @Override
            public ThrottlerKeyType key() {
                return ThrottlerKeyType.valueOf(ThrottlerKeyType.class, properties.getKeyType());
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return Throttle.class;
            }
        });
    }
}
