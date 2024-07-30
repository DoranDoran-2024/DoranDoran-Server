package com.sash.dorandoran.feign.client;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "clovaVoice", url = "https://naveropenapi.apigw.ntruss.com")
public interface ClovaVoiceClient {

    @PostMapping(value = "/tts-premium/v1/tts", consumes = "application/x-www-form-urlencoded")
    Response synthesizeSpeech(@RequestHeader("X-NCP-APIGW-API-KEY-ID") String clientId,
                              @RequestHeader("X-NCP-APIGW-API-KEY") String clientSecret,
                              @RequestParam("speaker") String speaker,
                              @RequestParam("text") String text,
                              @RequestParam("format") String format);
}