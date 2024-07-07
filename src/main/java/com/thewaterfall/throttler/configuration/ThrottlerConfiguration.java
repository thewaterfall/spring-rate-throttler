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
    public ThrottlerWebMvcConfigurer throttlerWebMvcConfigurer() {
        return new ThrottlerWebMvcConfigurer(throttlerInterceptor());
    }

    @Bean
    public ThrottlerInterceptor throttlerInterceptor()  {
      ThrottlerProcessor throttlerProcessor =
          new ThrottlerProcessor(throttlerInMemoryCache(), new ThrottlerKeyProcessor(), throttlerProperties());

      return new ThrottlerInterceptor(throttlerProcessor);
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
