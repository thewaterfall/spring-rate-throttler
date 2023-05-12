# Spring Rate Throttler

Spring Rate Throttler is a library designed for rate limiting or throttling incoming HTTP requests in a Spring 
application using annotations and application properties. Rate limiting is a common technique used to control the flow
of incoming requests to prevent overloading of server resources, ensuring that the server can function effectively for 
all clients.

With Spring Rate Throttler, you can easily define rate limiting policies based on criteria such as throttle key,
number of requests per unit of time, or other custom parameters. The library provides a customizable way to define these
policies through annotations or application properties, so you can quickly and easily add rate limiting capabilities 
to your Spring application.

## Features
- Easy integration with Spring Boot applications.
- Annotation and application properties based.
- Configurable rate limiting policies based on different criteria (global level, class level and method level configuration).
- Support for customizable rate limiting exception handling.
- In-memory caching using [Caffeine](https://github.com/ben-manes/caffeine) for high performance.
- [Token bucket](https://github.com/bbeck/token-bucket) algorithm for rate limiting.

## Installation
Spring Rate Throttler can be easily installed using JitPack, see Gradle and Maven examples below.

### Gradle
Add the following to your build.gradle file:

```
repositories {
    mavenCentral()
    maven { url "https://jitpack.io" }
}

dependencies {
    implementation 'com.github.thewaterfall:spring-rate-throttler:1.0.0'
}
```

### Maven
Add the following to your pom.xml file:

```
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.thewaterfall</groupId>
        <artifactId>spring-rate-throttler</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```

## Usage & configuration

There are three levels of configuration that can be applied: global, class and method. Lower level overrides higher 
levels. From the lowest (strongest) to the highest levels (weakest): method level > class level > global level. 

Library is based on such terms and properties as:
- **Throttle capacity**: initial and maximum capacity of requests
- **Throttle refill**: number of requests that are refilled (usually is equal to capacity)
- **Throttle refill period**: the period per which a number of requests (see refill property) are refilled
- **Throttle refill period unit**: the unit of time used for the refill period (in ChronoUnit, e.g. SECONDS, MINUTES, etc.)
- **Throttle key**: the type of key used to identify the source or user of the request (supported: IP_ADDRESS, HEADER)
- **Throttle key source**: the source of the key used to retrieve value from (header name, etc.)

### Throttler enabling

Add `@EnableThrottler` to enable throttler:

```
@EnableThrottler
@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		Application.run(Application.class, args);
	}
}
```

### Exception catching

When rate limit is exceeded, a `ThrottleException` will be thrown that can be caught and processed, see example
below.

```
@ControllerAdvice
public class ErrorHandler {
  @ExceptionHandler(value = ThrottleException.class)
  protected ResponseEntity<Object> handleThrottleException(ThrottleException e,
                                                           HttpServletRequest request) {
    return new ResponseEntity<>(response, TOO_MANY_REQUESTS);
  }
}
```

### Global level configuration

Global configuration is applied to all the requests and endpoints. Additionally, cache can be configured.

| Property                                              | Description                                                                                                                                                                                                                  |
|-------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| throttler.global.enabled                              | enables global configuration                                                                                                                                                                                                 |
| throttler.global.capacity                             | initial and maximum capacity of requests                                                                                                                                                                                     |
| throttler.global.refill                               | number of requests that are refilled (usually is equal to capacity)                                                                                                                                                          |
| throttler.global.refill-period                        | the period per which a number of requests (see refill property) are refilled                                                                                                                                                 |
| throttler.global.refill-period-unit                   | the unit of time used for the refill period (in ChronoUnit, e.g. SECONDS, MINUTES, etc.)                                                                                                                                     |
| throttler.global.key-type                             | the type of key used to identify the source or user of the request (supported: IP_ADDRESS, HEADER)                                                                                                                           |
| throttler.global.key-source                           | the source of the key used to retrieve value from (header name, etc.)                                                                                                                                                        |
| throttler.global.ignore-paths                         | comma separated list of paths to ignore (wildcards are supported, more on path patterns see [AntPathMatcher](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/util/AntPathMatcher.html)) |
| throttler.cache.in-memory.expire-after-access-seconds | instructs cache to expire after specified amount of seconds being not accessed (stale)                                                                                                                                       |
| throttler.cache.in-memory.max-size                    | sets up maximum size of cache                                                                                                                                                                                                |

### Class and method level configuration

Class level configuration overrides global level and method level overrides class level. Apply the `@Throttle` annotation
to enable throttle for the method (or whole class, i.e., every method).

Use `@Throttle(skip = true)` to exclude method from being throttled.

```

@Throttle(capacity = 10, refill = 10, period = 1, unit = ChronoUnit.MINUTES, key = ThrottlerKeyType.IP_ADDRESS)
@RestController
public class StorageController {
    @GetMapping("/images")
    public ResponseEntity<Resource> getImage(String name) {
        // Your endpoint logic here
    }
    
    @Throttle(capacity = 5, refill = 5, period = 1, unit = ChronoUnit.MINUTES, key = ThrottlerKeyType.IP_ADDRESS)
    @GetMapping("/videos")
    public ResponseEntity<Resource> getVideo(String name) {
        // Your endpoint logic here
    }
    
    @Throttle(skip = true)
    @GetMapping("/metadata")
    public ResponseEntity<String> getMetadata(String name) {
        // Your endpoint logic here
    }
}
```
Example description:
- StorageController is annotated with class level annotation that instructs to throttle all endpoints (class methods) as 
requests as 10 requests per minute for all. 
- Method "getVideo(..)" is overridden with method level annotation that instructs to throttle as 5 requests per minute. 
- Method "getMetadata(..)" is overridden with method level annotation that instructs to skip throttle and allow any rate.
