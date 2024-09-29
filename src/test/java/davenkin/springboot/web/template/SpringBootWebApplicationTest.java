package davenkin.springboot.web.template;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class SpringBootWebApplicationTest {

  @Autowired
  private WebTestClient webTestClient;

  @Test
  void shouldSayHelloWorld() {
    var result = this.webTestClient
        .get()
        .uri("/hello-world")
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBody(Map.class)
        .returnResult()
        .getResponseBody();
    assertEquals("Hello World!", result.get("value"));
  }
}
