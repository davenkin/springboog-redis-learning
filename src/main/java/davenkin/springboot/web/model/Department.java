package davenkin.springboot.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

// This class is a final class, it works with Jackson2JsonRedisSerializer, but not GenericJackson2JsonRedisSerializer configured by ObjectMapper.DefaultTyping.NON_FINAL
@Getter
@AllArgsConstructor
public final class Department {
    private String id;
    private String name;
}
