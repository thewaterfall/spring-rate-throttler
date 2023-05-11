package com.thewaterfall.throttler.processor.key.evaluator;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Interface for evaluating a throttler key from a HttpServletRequest.
 */
public interface ThrottlerKeyEvaluator {

    /**
     * Evaluates a throttler key from a HttpServletRequest.
     *
     * @param request The HttpServletRequest to evaluate the throttler key from.
     * @return The evaluated throttler key as a String.
     */
    String evaluate(HttpServletRequest request);
}
