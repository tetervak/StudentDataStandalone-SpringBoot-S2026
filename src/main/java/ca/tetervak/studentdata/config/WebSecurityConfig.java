package ca.tetervak.studentdata.config;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, PersistentTokenRepository persistentTokenRepository){

        // remove "h2-console" from the program in production
        httpSecurity.authorizeHttpRequests(
                (authorize) -> authorize
                        .requestMatchers(
                        "/css/**", "/js/**", "/", "/index", "/h2-console/**")
                        .permitAll()
                        .requestMatchers("/users/**")
                        .hasRole("ADMIN")
                        .anyRequest()
                        .authenticated()
        );

        // 1. Ignore CSRF protection specifically for the H2 console path
        httpSecurity.csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**")
        );

        // 2. Allow same-origin frames so the H2 console UI can load its inner frames
        httpSecurity.headers(headers -> headers
                .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
        );

        httpSecurity.formLogin(
                        (login) -> login
                                .loginPage("/login")
                                .defaultSuccessUrl("/index")
                                .failureUrl("/login?error=true")
                                .permitAll()
                );

        httpSecurity.logout(
                (logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/index")
                        .deleteCookies("remember-me")
                        .permitAll()
                );

        httpSecurity.rememberMe(
                (remember) -> remember
                        .rememberMeCookieName("remember-me")
                        .tokenRepository(persistentTokenRepository)
                        .tokenValiditySeconds(24 * 60 * 60)
        );

        return httpSecurity.build();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(DataSource dataSource) {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource); // there is no way to avoid this warning now
        return tokenRepository;
    }

    @Bean
    public SpringSecurityDialect securityDialect() {
        return new SpringSecurityDialect();
    }
}