package davenkin.springboot.web.helloworld;

import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HelloWorldController {
  private final HelloService helloService;

  @GetMapping(value = "/hello-world")
  public Map<String, String> helloWorld() {
    return Map.of("value", this.helloService.sayHello().name);
  }
}
