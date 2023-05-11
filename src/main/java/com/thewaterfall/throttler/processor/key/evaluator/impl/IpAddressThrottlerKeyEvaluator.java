package com.thewaterfall.throttler.processor.key.evaluator.impl;

import com.thewaterfall.throttler.processor.key.evaluator.ThrottlerKeyEvaluator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

/**
 * A throttler key evaluator that evaluates the IP address of the requester. If it failed to
 * evaluate IP address, ANONYMOUS string will be used as a returned key.
 */
public class IpAddressThrottlerKeyEvaluator implements ThrottlerKeyEvaluator {
    private static final String ANONYMOUS = "anonymous";

    @Override
    public String evaluate(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");

        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }

        if (StringUtils.hasText(ipAddress)) {
            return ipAddress;
        } else {
            return ANONYMOUS;
        }
    }
}
