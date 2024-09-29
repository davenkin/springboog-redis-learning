package davenkin.springboot.web.template.helloworld;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

  @GetMapping(value = "/hello-world")
  public Map<String, String> helloWorld() {
    return Map.of("value", "Hello World!");
  }
}
