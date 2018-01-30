package com.frank.config;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
/**
 * Created by Administrator on 2018年1月21日17:22:43
 */
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisFactory) {
        StringRedisTemplate template = new StringRedisTemplate(redisFactory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new
                Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

   /* @Bean(name = "redisConnectionFactory")
    RedisConnectionFactory factory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(masterHost);
        factory.setPort(masterPort);
        factory.setUsePool(true);
        factory.setConvertPipelineAndTxResults(true);
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMinIdle(50);
        poolConfig.setMaxTotal(500);
        poolConfig.setMaxWaitMillis(5000);
        poolConfig.setTestOnBorrow(true);
        factory.setPoolConfig(poolConfig);
        factory.setTimeout(5000);
        factory.afterPropertiesSet();
        return factory;
    }*/


}
