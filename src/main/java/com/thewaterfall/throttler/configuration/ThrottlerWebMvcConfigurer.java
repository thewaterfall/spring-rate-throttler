package com.thewaterfall.throttler.configuration;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class ThrottlerWebMvcConfigurer implements WebMvcConfigurer {

    private final HandlerInterceptor throttler;

    public ThrottlerWebMvcConfigurer(HandlerInterceptor throttler) {
        this.throttler = throttler;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(throttler);
    }
}
