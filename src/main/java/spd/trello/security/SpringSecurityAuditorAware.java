package spd.trello.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Slf4j
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @SuppressWarnings("NullableProblems")
    @Override
    public Optional<String> getCurrentAuditor() {
        log.info("The auditor was obtained successfully");
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getName);
    }
}
