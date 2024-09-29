package davenkin.springboot.web.template.helloworld;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HelloService {

    @Cacheable(value = "cache")
    public String sayHello() {
        log.info("This is not called by cache.");
        return "hello";
    }
}
