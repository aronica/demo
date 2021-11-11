package com.example.demo.configuration;

import com.example.demo.configuration.embedded.EmbeddedRedis;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import java.time.Duration;
import java.util.Arrays;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
//@EnableRedisRepositories
public class RedisRepositoryConfig {


    @org.springframework.context.annotation.Bean(name = "defaultRedisConnectionFactory")
    @ConditionalOnBean(EmbeddedRedis.class)
    public org.springframework.data.redis.connection.RedisConnectionFactory defaultRedisConnectionFactory() {
        return buildRedisConnectionFactoryByConsulUrl();
    }

    private org.springframework.data.redis.connection.RedisConnectionFactory buildRedisConnectionFactoryByConsulUrl() {

        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(
          Arrays.asList("localhost:6370"));
        ClusterTopologyRefreshOptions clusterTopologyRefreshOptions = ClusterTopologyRefreshOptions
          .builder().enableAllAdaptiveRefreshTriggers().enablePeriodicRefresh(Duration.ofSeconds(30)).build();
        ClusterClientOptions clusterClientOption = ClusterClientOptions
          .builder().topologyRefreshOptions(clusterTopologyRefreshOptions).validateClusterNodeMembership(true).build();
        org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration clientConfiguration = org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
          .builder()
          .readFrom(io.lettuce.core.ReadFrom.MASTER)
          .clientOptions(clusterClientOption)
          .commandTimeout(Duration.ofSeconds(3))
          .build();
        LettuceConnectionFactory factory = new LettuceConnectionFactory(redisClusterConfiguration, clientConfiguration);
        factory.setEagerInitialization(true);
        return factory;
    }


}
