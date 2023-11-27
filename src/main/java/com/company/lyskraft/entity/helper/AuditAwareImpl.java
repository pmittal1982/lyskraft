package com.company.lyskraft.entity.helper;

import com.company.lyskraft.entity.User;
import lombok.NonNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditAwareImpl implements AuditorAware<String> {
    @Override
    public @NonNull Optional<String> getCurrentAuditor() {
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null &&
                SecurityContextHolder.getContext().getAuthentication().getPrincipal().getClass() == User.class) {
            return Optional.ofNullable(SecurityContextHolder.getContext())
                    .map(SecurityContext::getAuthentication)
                    .filter(Authentication::isAuthenticated)
                    .map(Authentication::getPrincipal)
                    .map(User.class::cast)
                    .map(User::getUsername);
        }
        return Optional.of("anonymous");
    }
}