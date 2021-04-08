package com.jpabook.jpashop.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("spring.mail")
public class MailProperties {

    private String host;
    private int port;
    private String username;
    private String password;

}
