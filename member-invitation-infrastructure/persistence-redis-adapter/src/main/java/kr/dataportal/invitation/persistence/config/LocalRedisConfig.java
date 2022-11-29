package kr.dataportal.invitation.persistence.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author Heli
 * Created on 2022. 11. 30
 */
@Profile({"local"})
@Configuration
public class LocalRedisConfig {

    private final RedisServer redisServer = new RedisServer();

    @PostConstruct
    public void initRedis() {
        try {
            redisServer.start();
        } catch (Exception e) {
            // TODO logging
        }
    }

    @PreDestroy
    public void destroyRedis() {
        try {
            redisServer.stop();
        } catch (Exception e) {
            // TODO logging
        }
    }
}
