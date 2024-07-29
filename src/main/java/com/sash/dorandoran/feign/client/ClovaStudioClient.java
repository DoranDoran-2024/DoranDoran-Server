package com.sash.dorandoran.feign.client;

import com.sash.dorandoran.feign.config.ClovaStudioClientConfig;
import com.sash.dorandoran.feign.dto.ChatCompletionRequest;
import com.sash.dorandoran.feign.dto.ChatCompletionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "clova", url = "https://clovastudio.stream.ntruss.com/testapp/v1", configuration = ClovaStudioClientConfig.class)
public interface ClovaStudioClient {

    @PostMapping(value = "/chat-completions/HCX-003", consumes = "application/json", produces = "application/json")
    ChatCompletionResponse getChatCompletion(
            @RequestHeader("X-NCP-CLOVASTUDIO-API-KEY") String clovaStudioApiKey,
            @RequestHeader("X-NCP-APIGW-API-KEY") String apigwApiKey,
            @RequestHeader("X-NCP-CLOVASTUDIO-REQUEST-ID") String requestId,
            @RequestBody ChatCompletionRequest request);

}
