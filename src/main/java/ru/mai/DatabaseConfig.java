package ru.mai;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import ru.mai.model.Weather;

@Configuration
@EnableRedisRepositories
public class DatabaseConfig {

    @Bean
    public RedisConnectionFactory connectionFactory() {
        RedisConnectionFactory connectionFactory = new JedisConnectionFactory();
        return connectionFactory;
    }

    @Bean
    public RedisTemplate<Long, Weather> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Long, Weather> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        // Add some specific configuration here. Key serializers, etc.
        return template;
    }

}
