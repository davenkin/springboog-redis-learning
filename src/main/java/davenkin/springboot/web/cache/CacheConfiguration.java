package davenkin.springboot.web.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import davenkin.springboot.web.model.Department;
import davenkin.springboot.web.model.Employee;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static davenkin.springboot.web.cache.CacheConstants.DEPARTMENT_CACHE;
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

        //I would rather create Jackson2JsonRedisSerializer for each cached model to ensure our own model's freedom
        Jackson2JsonRedisSerializer<Employee> employeeJackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(cacheObjectMapper, Employee.class);
        Jackson2JsonRedisSerializer<Department> departmentJackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(cacheObjectMapper, Department.class);

        return builder -> builder.cacheDefaults(defaultCacheConfig()
                        .prefixCacheNameWith(CACHE_PREFIX)
                        .serializeValuesWith(fromSerializer(defaultSerializer))
                        .entryTtl(ofDays(1)))
                .withCacheConfiguration(EMPLOYEE_CACHE, defaultCacheConfig()
                        .prefixCacheNameWith(CACHE_PREFIX)
                        .serializeValuesWith(fromSerializer(employeeJackson2JsonRedisSerializer))
                        .entryTtl(ofDays(2)))
                .withCacheConfiguration(DEPARTMENT_CACHE, defaultCacheConfig()
                        .prefixCacheNameWith(CACHE_PREFIX)
                        .serializeValuesWith(fromSerializer(departmentJackson2JsonRedisSerializer))
                        .entryTtl(ofDays(2)));
    }


// The below is using GenericJackson2JsonRedisSerializer, which is not good
//    @Bean
//    public RedisCacheManagerBuilderCustomizer redisBuilderCustomizer(ObjectMapper objectMapper) {
//        ObjectMapper cacheObjectMapper = objectMapper.copy();
//        cacheObjectMapper.activateDefaultTyping(cacheObjectMapper.getPolymorphicTypeValidator(),
//                ObjectMapper.DefaultTyping.NON_FINAL,
//                PROPERTY);
//        GenericJackson2JsonRedisSerializer defaultSerializer = new GenericJackson2JsonRedisSerializer(cacheObjectMapper);
//
//        return builder -> builder.cacheDefaults(defaultCacheConfig()
//                        .prefixCacheNameWith(CACHE_PREFIX)
//                        .serializeValuesWith(fromSerializer(defaultSerializer))
//                        .entryTtl(ofDays(1)))
//                .withCacheConfiguration(EMPLOYEE_CACHE, defaultCacheConfig()
//                        .prefixCacheNameWith(CACHE_PREFIX)
//                        .serializeValuesWith(fromSerializer(defaultSerializer))
//                        .entryTtl(ofDays(2)));
//    }

}
