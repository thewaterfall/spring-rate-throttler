package com.thewaterfall.throttler.processor;

import java.lang.reflect.Method;
import com.thewaterfall.throttler.processor.key.ThrottlerKey;
import com.thewaterfall.throttler.processor.key.ThrottlerKeyType;
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
     * @param type The {@code ThrottlerKeyType} object representing the type of key used to identify
     *             the source or user of the request.
     * @return A {@code ThrottlerKey} object representing the key used for throttling.
     */
    public ThrottlerKey process(HttpServletRequest request, Method method, ThrottlerKeyType type) {
        return new ThrottlerKey(method, type, type.getEvaluator().evaluate(request));
    }
}
