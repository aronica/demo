package com.example.demo.configuration.embedded;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.stereotype.Component;
import redis.embedded.Redis;
import redis.embedded.cluster.RedisCluster;

@Component
public class EmbeddedRedis {

    private final List<Integer> masterPorts = Arrays.asList(6370, 6371, 6372);
    private final List<Integer> slavePorts = Arrays.asList(6380, 6381, 6382);

    private Redis redisServer;

    @PostConstruct
    public void startRedis() {

        redisServer = new RedisCluster.Builder()
          .serverPorts(Stream.concat(masterPorts.stream(), slavePorts.stream())
            .collect(Collectors.toList()))
          .build();

        redisServer.start();
    }

    @PreDestroy
    public void stopRedis() {
        Optional.ofNullable(redisServer).ifPresent(Redis::stop);
    }
}
