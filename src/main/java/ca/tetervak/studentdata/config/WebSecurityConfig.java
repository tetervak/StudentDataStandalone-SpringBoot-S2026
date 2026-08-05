package ca.tetervak.studentdata.config;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
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

        // this line is necessary for h2-console, it reduces security
        //httpSecurity.csrf(AbstractHttpConfigurer::disable);

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
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }

    @Bean
    public SpringSecurityDialect securityDialect() {
        return new SpringSecurityDialect();
    }
}