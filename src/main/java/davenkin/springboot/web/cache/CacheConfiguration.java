package davenkin.springboot.web.cache;

import static java.time.Duration.ofDays;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping.NON_FINAL;
import static davenkin.springboot.web.cache.CacheConstants.REDIS_LEARNING_CACHE;
import static org.springframework.data.redis.cache.RedisCacheConfiguration.defaultCacheConfig;
import static org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair.fromSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

@EnableCaching
@Configuration(proxyBeanMethods = false)
public class CacheConfiguration {
  private static final String CACHE_PREFIX = "Cache:";

  @Bean
  public RedisCacheManagerBuilderCustomizer redisBuilderCustomizer(ObjectMapper objectMapper) {
    ObjectMapper cacheObjectMapper = objectMapper.copy();
    cacheObjectMapper.activateDefaultTyping(cacheObjectMapper.getPolymorphicTypeValidator(), NON_FINAL, PROPERTY);
    GenericJackson2JsonRedisSerializer defaultSerializer = new GenericJackson2JsonRedisSerializer(cacheObjectMapper);

    return builder -> builder.cacheDefaults(defaultCacheConfig()
            .prefixCacheNameWith(CACHE_PREFIX)
            .serializeValuesWith(fromSerializer(defaultSerializer))
            .entryTtl(ofDays(1)))
        .withCacheConfiguration(REDIS_LEARNING_CACHE, defaultCacheConfig()
            .prefixCacheNameWith(CACHE_PREFIX)
            .serializeValuesWith(fromSerializer(defaultSerializer))
            .entryTtl(ofDays(7)));
  }
}
