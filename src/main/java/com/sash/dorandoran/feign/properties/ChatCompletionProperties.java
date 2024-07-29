package com.sash.dorandoran.feign.properties;


import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "clova.chat-completion")
@Data
public class ChatCompletionProperties {

    private String apiKey;
    private String apigwKey;
    private String requestId;

}
