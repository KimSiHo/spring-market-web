package com.jpabook.jpashop.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("spring.mail.properties.mail.smtp")
public class MailPropProperties {

    private Boolean auth;
    private int timeout;
    private Starttls starttls;

    @Data
    public static class Starttls{
        private Boolean enable;
    }

}
