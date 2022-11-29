package kr.dataportal.invitation.persistence.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

/**
 * @author Heli
 * Created on 2022. 11. 30
 */
@Service
public class LettuceRedisService implements RedisService {

    private static final String KEY_PREFIX = "dataportal.kr_invitaion:";

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    public LettuceRedisService(final StringRedisTemplate redisTemplate, final ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public <T> Optional<T> get(final String key, final Class<T> type) {
        String serializedValue = redisTemplate.opsForValue().get(redisKey(key));
        try {
            return Optional.of(objectMapper.readValue(serializedValue, type));
        } catch (Exception e) {
            // TODO logging
        }
        return Optional.empty();
    }

    @Override
    public void set(final String key, final Object value, final Duration duration) {
        try {
            String serializedValue = objectMapper.writeValueAsString(value);
            redisTemplate.opsForValue().set(redisKey(key), serializedValue, duration);
        } catch (Exception e) {
            // TODO logging
        }
    }

    @Override
    public boolean setIfAbsent(final String key, final Object value, final Duration duration) {
        try {
            String serializedValue = objectMapper.writeValueAsString(value);
            return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(redisKey(key), serializedValue, duration));
        } catch (Exception e) {
            // TODO logging
        }
        return false;
    }

    @Override
    public boolean delete(final String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(redisKey(key)));
    }

    private String redisKey(final String key) {
        return KEY_PREFIX + key;
    }
}
