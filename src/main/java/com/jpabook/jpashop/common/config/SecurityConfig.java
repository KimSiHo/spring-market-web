package com.jpabook.jpashop.common.config;


import com.jpabook.jpashop.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

     private final AccountService accountService;
     private final DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/", "/login", "sign-up",
                        "email-login", "/check-email-login", "/login-link", "/bbs/**").permitAll()
                .mvcMatchers(HttpMethod.GET,  "/bbs/**").permitAll()
                .mvcMatchers(HttpMethod.POST, "/bbs/**", "/bbs/api/v1/posts/**").permitAll()
                .mvcMatchers(HttpMethod.PUT, "/bbs/**").permitAll()
                .mvcMatchers(HttpMethod.DELETE, "/bbs/**").permitAll()
                .anyRequest().authenticated();

                 http.formLogin()
                         .loginPage("/login").permitAll();
                 
                 http.logout()
                         .logoutSuccessUrl("/");

                 http.rememberMe()
                       .userDetailsService(accountService)
                       .tokenRepository(tokenRepository());
    }

    @Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .mvcMatchers("/node_modules/**")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

}
