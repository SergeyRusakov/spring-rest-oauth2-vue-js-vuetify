package ru.sergeyrusakov.testingTask.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import ru.sergeyrusakov.testingTask.entities.GitHubOauth2User;
import ru.sergeyrusakov.testingTask.service.GitHubUserService;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .oauth2Login().userInfoEndpoint(userInfo->userInfo.userService(this.service())
                 .customUserType(GitHubOauth2User.class,"github"))
                .and()
                .cors(Customizer.withDefaults())
                .csrf(c -> c
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .sessionManagement().maximumSessions(-1).sessionRegistry(sessionRegistry());
//            http.sessionManagement().sessionFixation().migrateSession()
//                    .sessionAuthenticationStrategy(registerSessionAuthenticationStrategy());
    }

    @Bean
    protected GitHubUserService service(){
        return new GitHubUserService();
    }

    @Bean
    protected SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl();
    }

}
