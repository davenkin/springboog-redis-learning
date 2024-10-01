package davenkin.springboot.web.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static davenkin.springboot.web.cache.CacheConstants.EMPLOYEE_CACHE;
import static java.time.Duration.ofDays;
import static org.springframework.data.redis.cache.RedisCacheConfiguration.defaultCacheConfig;
import static org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair.fromSerializer;

@EnableCaching
@Configuration(proxyBeanMethods = false)
public class CacheConfiguration {
    private static final String CACHE_PREFIX = "Cache:";

    @Bean
    public RedisCacheManagerBuilderCustomizer redisBuilderCustomizer(ObjectMapper objectMapper) {
        ObjectMapper cacheObjectMapper = objectMapper.copy();
        cacheObjectMapper.activateDefaultTyping(cacheObjectMapper.getPolymorphicTypeValidator(),
                ObjectMapper.DefaultTyping.NON_FINAL,
                PROPERTY);
        GenericJackson2JsonRedisSerializer defaultSerializer = new GenericJackson2JsonRedisSerializer(cacheObjectMapper);

        return builder -> builder.cacheDefaults(defaultCacheConfig()
                        .prefixCacheNameWith(CACHE_PREFIX)
                        .serializeValuesWith(fromSerializer(defaultSerializer))
                        .entryTtl(ofDays(1)))
                .withCacheConfiguration(EMPLOYEE_CACHE, defaultCacheConfig()
                        .prefixCacheNameWith(CACHE_PREFIX)
                        .serializeValuesWith(fromSerializer(defaultSerializer))
                        .entryTtl(ofDays(2)));
    }
}
