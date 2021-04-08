package com.jpabook.jpashop.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private final MailProperties mailProperties;
    private final MailPropProperties mailPropProperties;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public JavaMailSender javaMailSender(){

        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost(mailProperties.getHost());
        javaMailSender.setPort(mailProperties.getPort());
        javaMailSender.setUsername(mailProperties.getUsername());
        javaMailSender.setPassword(mailProperties.getPassword());

        Properties javaMailProperties = javaMailSender.getJavaMailProperties();

        javaMailProperties.put("mail.smtp.starttls.enable", mailPropProperties.getStarttls().getEnable());
        javaMailProperties.put("mail.smtp.auth", mailPropProperties.getAuth());
        javaMailProperties.put("mail.debug", true);

        javaMailSender.setJavaMailProperties(javaMailProperties);

        return javaMailSender;
    }
}
