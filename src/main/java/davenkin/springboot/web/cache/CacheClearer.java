package davenkin.springboot.web.cache;

import static davenkin.springboot.web.cache.CacheConstants.REDIS_LEARNING_CACHE;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CacheClearer {

  @Caching(evict = {
      @CacheEvict(value = REDIS_LEARNING_CACHE, allEntries = true),
  })
  public void evictAllCache() {
    log.info("Evicted all cache.");
  }
}
