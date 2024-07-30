package com.sash.dorandoran.feign.client;

import com.sash.dorandoran.feign.dto.KakaoUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakaoClient", url = "https://kapi.kakao.com")
public interface KakaoUserInfoClient {

    @GetMapping("/v2/user/me")
    KakaoUserResponse getUserInfo(@RequestHeader("Authorization") String accessToken,
                                  @RequestHeader("Content-type") String contentType,
                                  @RequestParam(value = "secure_resource", required = false) boolean secureResource,
                                  @RequestParam(value = "property_keys", required = false) String propertyKeys);

}