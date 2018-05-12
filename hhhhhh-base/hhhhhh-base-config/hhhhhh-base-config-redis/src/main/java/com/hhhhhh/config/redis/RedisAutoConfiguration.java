package com.hhhhhh.config.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

@Configuration
@EnableConfigurationProperties({RedisProperties.class})
@ConditionalOnClass({JedisPool.class})
@ConditionalOnProperty(
        prefix = "redis",
        havingValue = "false",
        value = {"cluster"},
        matchIfMissing = false
)
public class RedisAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(RedisAutoConfiguration.class);

    @Resource
    private RedisProperties properties;

    public RedisAutoConfiguration() {}

    @Bean
    public JedisPool jedisPool() {
        return new JedisPool(this.properties.getSingleHost(), this.properties.getSinglePort().intValue());
    }
}
