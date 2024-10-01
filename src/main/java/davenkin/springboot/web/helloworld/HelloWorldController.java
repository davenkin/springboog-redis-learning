package davenkin.springboot.web.helloworld;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HelloWorldController {
    @GetMapping(value = "/hello-world")
    public Map<String, String> helloWorld() {
        return Map.of("value", "Hello World!");
    }
}
