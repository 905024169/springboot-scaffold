package com.ztech.common.redis;


import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;


@Configuration
@EnableCaching//开启注解
public class RedisConfig extends CachingConfigurerSupport {

    @Bean
    @Primary
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(1)); // 设置缓存有效期一小时
        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(redisCacheConfiguration).build();
    }


    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // set key serializer
        StringRedisSerializer serializer = new StringRedisSerializer();
        // 设置key序列化类，否则key前面会多了一些乱码
        template.setKeySerializer(serializer);
        template.setHashKeySerializer(serializer);
        // fastjson serializer
        GenericFastJsonRedisSerializer fastSerializer = new GenericFastJsonRedisSerializer();
        template.setValueSerializer(fastSerializer);
        template.setHashValueSerializer(fastSerializer);
        // 如果 KeySerializer 或者 ValueSerializer 没有配置，则对应的 KeySerializer、ValueSerializer 才使用这个 Serializer
        template.setDefaultSerializer(fastSerializer);
        LettuceConnectionFactory factory = (LettuceConnectionFactory) redisConnectionFactory;
        // factory
        template.setConnectionFactory(redisConnectionFactory);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public RedisFactory redisFactory(RedisConnectionFactory redisConnectionFactory) {
        RedisFactory redisFactory = new RedisFactory();
        redisFactory.setRedisTemplate(redisTemplate(redisConnectionFactory));
        return redisFactory;
    }
}

