package kr.dataportal.invitation.persistence.config;

import kr.dataportal.invitation.persistence.Persistence;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Heli
 * Created on 2022. 11. 30
 */
@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(
        basePackageClasses = {Persistence.class}
)
public class JpaConfiguration {
}
