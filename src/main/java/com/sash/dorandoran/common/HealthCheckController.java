package com.sash.dorandoran.common;

import com.sash.dorandoran.common.response.ResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/health-check")
    public ResponseDto<Boolean> healthCheck() {
        return ResponseDto.onSuccess(Boolean.TRUE);
    }

}
