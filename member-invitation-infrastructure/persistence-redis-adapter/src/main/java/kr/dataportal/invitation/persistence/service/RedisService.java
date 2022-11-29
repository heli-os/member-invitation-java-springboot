package kr.dataportal.invitation.persistence.service;

import java.time.Duration;
import java.util.Optional;

/**
 * @author Heli
 * Created on 2022. 11. 30
 */
public interface RedisService {
    <T> Optional<T> get(final String key, final Class<T> type);

    void set(final String key, final Object value, final Duration duration);

    boolean setIfAbsent(final String key, final Object value, final Duration duration);

    boolean delete(final String key);
}
