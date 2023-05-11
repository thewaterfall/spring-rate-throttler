package com.thewaterfall.throttler.configuration;

import com.thewaterfall.throttler.processor.ThrottlerProcessor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

public class ThrottlerInterceptor implements HandlerInterceptor {

    private final ThrottlerProcessor throttler;

    public ThrottlerInterceptor(ThrottlerProcessor throttler) {
        this.throttler = throttler;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        return throttler.throttle(request, ((HandlerMethod) handler).getMethod());
    }
}
