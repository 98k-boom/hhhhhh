package com.hhhhhh.config.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "redis")
public class RedisProperties {

    private String singleHost;
    private Integer singlePort;
    private String clusterNodes;
    private String password;
    private Boolean cluster;

    public RedisProperties() {}

    public String getSingleHost() {
        return this.singleHost;
    }

    public void setSingleHost(String singleHost) {
        this.singleHost = singleHost;
    }

    public Integer getSinglePort() {
        return this.singlePort;
    }

    public void setSinglePort(Integer singlePort) {
        this.singlePort = singlePort;
    }

    public String getClusterNodes() {
        return this.clusterNodes;
    }

    public void setClusterNodes(String clusterNodes) {
        this.clusterNodes = clusterNodes;
    }

    public Boolean getCluster() {
        return this.cluster;
    }

    public void setCluster(Boolean cluster) {
        this.cluster = cluster;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
