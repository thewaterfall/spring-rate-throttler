package com.thewaterfall.throttler.processor.key.evaluator.impl;

import com.thewaterfall.throttler.configuration.annotation.Throttle;
import com.thewaterfall.throttler.processor.key.evaluator.ThrottlerKeyEvaluator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public class CookieThrottlerKeyEvaluator implements ThrottlerKeyEvaluator {
    private static final String ANONYMOUS = "anonymous";

    @Override
    public String evaluate(HttpServletRequest request, Throttle throttle) {
        return getCookieByName(request, throttle.source())
                .map(Cookie::getValue)
                .orElse(ANONYMOUS);
    }

    public Optional<Cookie> getCookieByName(HttpServletRequest request, String name) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(name)) {
                    return Optional.of(cookie);
                }
            }
        }

        return Optional.empty();
    }
}
