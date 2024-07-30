package com.sash.dorandoran.feign.client;

import com.sash.dorandoran.feign.dto.NicknameResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "nickNameClient",
        url = "https://nickname.hwanmoo.kr"
)
public interface NicknameClient {

    @GetMapping
    NicknameResponse getNickName(@RequestParam(defaultValue = "json") String format, @RequestParam(defaultValue = "1") int count, @RequestParam(defaultValue = "8") int max_length);

}
