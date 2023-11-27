package com.company.lyskraft.configuration;

import com.company.lyskraft.entity.helper.AuditAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "customAuditProvider")
public class AuditConfiguration {
    @Bean
    public AuditorAware<String> customAuditProvider() {
        return new AuditAwareImpl();
    }
}
