package com.thewaterfall.throttler.configuration;

import com.thewaterfall.throttler.cache.ThrottlerInMemoryCache;
import com.thewaterfall.throttler.configuration.properties.ThrottlerProperties;
import com.thewaterfall.throttler.processor.ThrottlerKeyProcessor;
import com.thewaterfall.throttler.processor.ThrottlerProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThrottlerConfiguration {

    @Bean
    public ThrottlerWebMvcConfigurer throttlerWebMvcConfigurer(ThrottlerInMemoryCache cache,
                                                               ThrottlerProperties properties) {
        ThrottlerProcessor throttlerProcessor =
            new ThrottlerProcessor(cache, new ThrottlerKeyProcessor(), properties);

        return new ThrottlerWebMvcConfigurer(new ThrottlerInterceptor(throttlerProcessor));
    }


    @Bean
    public ThrottlerProperties throttlerProperties() {
        return new ThrottlerProperties();
    }

    @Bean
    public ThrottlerInMemoryCache throttlerInMemoryCache() {
        return new ThrottlerInMemoryCache();
    }

}
