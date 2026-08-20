package ca.tetervak.studentdata.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing // automatically maintain creation and modification timestamps
public class JpaDataConfig {
}
