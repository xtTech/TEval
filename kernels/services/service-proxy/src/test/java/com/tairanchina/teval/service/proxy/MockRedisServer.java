package com.tairanchina.teval.service.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.embedded.RedisServer;

import java.io.IOException;

public class MockRedisServer {

    private static final Logger logger = LoggerFactory.getLogger(MockRedisServer.class);

    private RedisServer redisServer;

    public MockRedisServer() throws IOException {
        redisServer = new RedisServer();
        if (!redisServer.isActive()) {
            try {
                logger.info("Start Mock Redis.");
                redisServer.start();
                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    if (redisServer.isActive()) {
                        redisServer.stop();
                    }
                }));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}