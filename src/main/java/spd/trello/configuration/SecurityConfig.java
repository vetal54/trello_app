package spd.trello.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import spd.trello.security.JwtConfigurer;
import spd.trello.security.SpringSecurityAuditorAware;

@Configuration
@EnableWebSecurity
@EnableJpaAuditing(auditorAwareRef = "auditProvider")
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtConfigurer jwtConfigurer;

    public SecurityConfig(JwtConfigurer jwtConfigurer) {
        this.jwtConfigurer = jwtConfigurer;
    }

    private static final String[] AUTH_WHITELIST = {
            "/authenticate",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/v2/api-docs",
            "/webjars/**"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/user/register").permitAll()
                .antMatchers("/user/login").permitAll()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(jwtConfigurer).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public AuditorAware<String> auditProvider() {
        return new SpringSecurityAuditorAware();
    }
}