package ru.gb.web_market.products.ceche;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import ru.gb.web_market.api.dto.CategoryDto;
import ru.gb.web_market.products.entities.Category;
import ru.gb.web_market.products.entities.Manufacturer;

import java.util.List;

@Configuration
public class CacheConfig {

    @Bean(value = "listCategoryRedis")
    public RedisTemplate<String, List<CategoryDto>> listCategoryRedis(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, List<CategoryDto>> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(List.class));
        return template;
    }

    @Bean(value = "categoryRedis")
    public RedisTemplate<String, Category> categoryRedis(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Category> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }

    @Bean(value = "listManufacturerRedis")
    public RedisTemplate<String, List<String>> listManufacturerRedis(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, List<String>> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(List.class));
        return template;
    }

    @Bean(value = "manufacturerRedis")
    public RedisTemplate<String, Manufacturer> manufacturerRedis(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Manufacturer> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }

    @Bean(value = "stringRedis")
    public RedisTemplate<Long, String> stringRedis(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Long, String> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }
}
