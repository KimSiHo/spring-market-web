package com.jpabook.jpashop.common.aws.dto;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "cloud.aws.s3")
@Component
public class S3Component {

    private String bucket;
}
