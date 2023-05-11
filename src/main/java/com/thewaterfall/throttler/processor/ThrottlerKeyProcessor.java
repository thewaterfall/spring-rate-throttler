package com.thewaterfall.throttler.processor;

import java.lang.reflect.Method;
import com.thewaterfall.throttler.configuration.annotation.Throttle;
import com.thewaterfall.throttler.processor.key.ThrottlerKey;
import jakarta.servlet.http.HttpServletRequest;

/**
 * The {@code ThrottlerKeyProcessor} class is responsible for creating a {@code ThrottlerKey} object
 * based on the request and method being throttled.
 */
public class ThrottlerKeyProcessor {

    /**
     * Creates a {@code ThrottlerKey} object based on the request and method being throttled.
     *
     * @param request The {@code HttpServletRequest} object representing the incoming request.
     * @param method The {@code Method} object representing the method being throttled.
     * @param throttle The {@code Throttle} object representing annotation with throttle data.
     * @return A {@code ThrottlerKey} object representing the key used for throttling.
     */
    public ThrottlerKey process(HttpServletRequest request, Method method, Throttle throttle) {
        return new ThrottlerKey(method, throttle.key(),
            throttle.key().getEvaluator().evaluate(request, throttle));
    }
}
