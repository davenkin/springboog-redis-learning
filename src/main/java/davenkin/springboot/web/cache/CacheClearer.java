package davenkin.springboot.web.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import static davenkin.springboot.web.cache.CacheConstants.EMPLOYEE_CACHE;

@Slf4j
@Component
@RequiredArgsConstructor
public class CacheClearer {

    @Caching(evict = {
            @CacheEvict(value = EMPLOYEE_CACHE, allEntries = true),
    })
    public void evictAllCache() {
        log.info("Evicted all cache.");
    }
}
