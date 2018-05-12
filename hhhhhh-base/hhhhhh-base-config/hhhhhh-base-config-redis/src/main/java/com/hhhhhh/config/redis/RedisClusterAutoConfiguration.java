package com.hhhhhh.config.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.HashSet;

@Configuration
@EnableConfigurationProperties({RedisProperties.class})
@ConditionalOnClass({JedisPool.class})
@ConditionalOnProperty(
        prefix = "redis",
        havingValue = "true",
        value = {"cluster"},
        matchIfMissing = false
)
public class RedisClusterAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(RedisClusterAutoConfiguration.class);

    @Resource
    private RedisProperties properties;

    public RedisClusterAutoConfiguration() {}

    @Bean
    public JedisCluster jedisCluster() {
        String clusterNodes = this.properties.getClusterNodes();
        if(StringUtils.isEmpty(clusterNodes)) {
            logger.error("clusterNodes不能为空!");
            throw new RuntimeException();
        } else {
            String[] nodes = clusterNodes.split(",");
            HashSet<HostAndPort> hostAndPorts = new HashSet();
            String[] var4 = nodes;
            int var5 = nodes.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                String node = var4[var6];
                String[] split = node.split(":");
                HostAndPort hostAndPort = new HostAndPort(split[0], Integer.parseInt(split[1]));
                hostAndPorts.add(hostAndPort);
            }

            JedisCluster jedisCluster = new JedisCluster(hostAndPorts);
            return jedisCluster;
        }
    }
}
