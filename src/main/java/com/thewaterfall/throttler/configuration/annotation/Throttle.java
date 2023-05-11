package com.thewaterfall.throttler.configuration.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.temporal.ChronoUnit;
import com.thewaterfall.throttler.processor.key.ThrottlerKeyType;

/**
 * The {@code @Throttle} annotation is used to specify the throttling parameters for a method
 * or a class. Throttling is a mechanism that limits the rate at which requests are processed
 * in order to prevent overloading a system or a service. The {@code @Throttle} annotation can
 * be used on methods and classes. When used on a method, it specifies the throttling parameters
 * for that method. When used on a class, it specifies the throttling parameters for all methods
 * in the class that do not have their own {@code @Throttle} annotation.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Throttle {

    /**
     * The maximum number of requests that can be processed in a single period.
     *
     * @return capacity
     */
    int capacity();

    /**
     * The number of requests that are refilled per specified period (see period field).
     *
     * @return refill
     */
    int refill();

    /**
     * The period per which a number of requests (see refill field) are refilled
     *
     * @return period
     */
    int period();

    /**
     * The unit of time used for the period.
     *
     * @return unit
     */
    ChronoUnit unit() default ChronoUnit.SECONDS;

    /**
     * The type of key used to identify the source or user of the request.
     *
     * @return key
     */
    ThrottlerKeyType key() default ThrottlerKeyType.IP_ADDRESS;
}
