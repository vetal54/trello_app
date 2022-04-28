package spd.trello.security;

import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Log4j2
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @SuppressWarnings("NullableProblems")
    @Override
    public Optional<String> getCurrentAuditor() {
        log.trace("Entering getCurrentAuditor() method");
        log.info("The auditor was obtained successfully");
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getName);
    }
}
