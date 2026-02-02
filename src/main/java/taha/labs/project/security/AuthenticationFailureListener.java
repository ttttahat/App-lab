package taha.labs.project.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent; // Add this
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFailureListener {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationFailureListener.class);

    @EventListener
    public void onFailure(AuthenticationFailureBadCredentialsEvent event) {
        String username = event.getAuthentication().getName();

        log.warn("SECURITY ALERT: Failed login attempt for username: {}", username);
    }

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent event) {
        String username = event.getAuthentication().getName();

        log.info("LOGIN SUCCESS: User [{}] has accessed the system.", username);
    }
}