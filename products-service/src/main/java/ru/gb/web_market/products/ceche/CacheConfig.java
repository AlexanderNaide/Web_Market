package ru.gb.web_market.products.ceche;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import ru.gb.web_market.api.dto.CategoryDto;
import ru.gb.web_market.products.entities.Category;

import java.util.List;

@Configuration
public class CacheConfig {

    @Bean(value = "listCategoryRedis")
    public RedisTemplate<Long, List<CategoryDto>> listCategoryRedis(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Long, List<CategoryDto>> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }

    @Bean(value = "categoryRedis")
    public RedisTemplate<Long, Category> categoryRedis(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Long, Category> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }

}
