package ro.store.management.tool.config;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ro.store.management.tool.exception.UserAuthenticationErrorHandler;
import ro.store.management.tool.login.LoginAuthenticationProvider;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    public WebSecurityConfig() {
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationProvider authenticationProvider,
                                                   AuthenticationEntryPoint authenticationEntryPoint)
                                throws Exception {

        http.csrf(csrfCustomizer -> csrfCustomizer
                .disable());
        http.authorizeHttpRequests(authorizeHttpRequestsCustomizer -> authorizeHttpRequestsCustomizer
                .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/store/v1/find-product"))
                .hasAnyRole("USER", "ADMIN")
                .requestMatchers(new AntPathRequestMatcher("/store/v1/save-product"))
                .hasAnyRole("ADMIN")
                .anyRequest()
                .authenticated()
        );

        http.formLogin(formLoginCustomizer -> formLoginCustomizer
                .disable()
        );

        http.httpBasic(httpBasicCustomizer -> httpBasicCustomizer
                .authenticationEntryPoint(authenticationEntryPoint));

        http.logout(logoutCustomizer -> logoutCustomizer
                .disable()
        );

        http.anonymous(anonymousCustomizer -> anonymousCustomizer
                .disable()
        );

        http.authenticationProvider(authenticationProvider);

        return http.build();
    }

    @Bean
    public AuthenticationEntryPoint userAuthenticationErrorHandler() {
        UserAuthenticationErrorHandler userAuthenticationErrorHandler = new UserAuthenticationErrorHandler();
        userAuthenticationErrorHandler.setRealmName("Basic Authentication");
        return userAuthenticationErrorHandler;
    }

    @Bean
    public LoginAuthenticationProvider loginAuthenticationProvider() {
        return new LoginAuthenticationProvider();
    }
}