package kr.dataportal.invitation.persistence.config;

import com.google.common.annotations.VisibleForTesting;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Heli
 * Created on 2022. 11. 30
 */
@MappedSuperclass
public abstract class BaseEntity<T extends BaseEntity<T>> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime lastModifiedAt;

    @PrePersist
    void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        lastModifiedAt = now;
    }

    @PreUpdate
    void preUpdate() {
        lastModifiedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getLastModifiedAt() {
        return lastModifiedAt;
    }

    @SuppressWarnings("unchecked")
    @VisibleForTesting
    public T apply(final long id, final LocalDateTime now) {
        this.id = id;
        this.createdAt = now;
        this.lastModifiedAt = now;
        return (T) this;
    }
}
