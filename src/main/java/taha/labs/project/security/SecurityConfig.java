package taha.labs.project.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy; 
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class SecurityConfig { 
//this class enforces route protection

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/welcome", "/login", "/register", "/css/**", "/js/**").permitAll() 
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) 
            )
            .formLogin(form -> form
                .loginPage("/login")
                .usernameParameter("username") 
                .defaultSuccessUrl("/user/home", true)
                .failureHandler((request, response, exception) -> {
                    String username = request.getParameter("username");
                    logger.warn("SECURITY ALERT: Failed login attempt for username: {}", username);
                    response.sendRedirect("/login?error");
                })
                .permitAll()
            )
            .exceptionHandling(ex -> ex
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    logger.warn("SECURITY ALERT: Unauthorized URL access attempt to {}", request.getRequestURI());
                    response.sendRedirect("/login");
                })
            )
            // logout configuration below handles session invalidation
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .clearAuthentication(true) 
                .deleteCookies("JSESSIONID", "remember-me") 
                .permitAll()
            )
            .headers(headers -> headers
                .contentTypeOptions(Customizer.withDefaults())
                .frameOptions(frame -> frame.deny())
                .referrerPolicy(ref -> ref.policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.NO_REFERRER))
                .contentSecurityPolicy(csp -> csp
                    .policyDirectives("default-src 'self'; style-src 'self' 'unsafe-inline';")
                )
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}