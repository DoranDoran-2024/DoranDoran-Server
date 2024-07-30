package com.sash.dorandoran.feign.client;

import com.sash.dorandoran.feign.dto.NaverTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "naverRequestToken", url = "${naver.request-token-uri}")
public interface NaverRequestTokenClient {

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    NaverTokenResponse getToken(@RequestBody Map<String, ?> form);

}
