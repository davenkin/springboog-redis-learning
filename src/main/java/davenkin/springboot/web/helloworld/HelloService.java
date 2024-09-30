package davenkin.springboot.web.helloworld;

import static davenkin.springboot.web.cache.CacheConstants.REDIS_LEARNING_CACHE;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class HelloService {

  @Cacheable(value = REDIS_LEARNING_CACHE)
  public String sayHello() {
    log.info("This is not called by cache.");
    return "hello";
  }
}
