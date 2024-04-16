package ro.store.management.tool.login;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class LoginAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!UsernamePasswordAuthenticationToken.class.isInstance(authentication)) {
            throw new InternalAuthenticationServiceException("Only UsernamePasswordAuthenticationToken is supported");
        }
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        if (username.equals("user") && password.equals("user")) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_USER");
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(simpleGrantedAuthority);
            return UsernamePasswordAuthenticationToken.authenticated(username, password, authorities);
        } else if (username.equals("admin") && password.equals("admin")) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(simpleGrantedAuthority);
            return UsernamePasswordAuthenticationToken.authenticated(username, password, authorities);
        } else {
            throw new BadCredentialsException("Bad credentials");
        }
    }

    @Override
    public boolean supports(Class<?> authenticationClass) {
        return authenticationClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
