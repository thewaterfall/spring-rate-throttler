package com.thewaterfall.throttler.processor.key.evaluator.impl;

import com.thewaterfall.throttler.configuration.annotation.Throttle;
import com.thewaterfall.throttler.processor.key.evaluator.ThrottlerKeyEvaluator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

public class HeaderThrottlerKeyEvaluator implements ThrottlerKeyEvaluator {
    private static final String ANONYMOUS = "anonymous";

    @Override
    public String evaluate(HttpServletRequest request, Throttle throttle) {
        String header = request.getHeader(throttle.source());

        if (StringUtils.hasText(header)) {
            return header;
        } else {
            return ANONYMOUS;
        }
    }
}
