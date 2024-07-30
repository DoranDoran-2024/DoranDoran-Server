package com.sash.dorandoran.feign.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "naver")
public class NaverProperties {

    private String baseUrl;
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String requestTokenUri;
    private String userInfoUri;

}
