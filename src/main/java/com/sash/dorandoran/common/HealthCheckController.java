package com.sash.dorandoran.common;

import com.sash.dorandoran.common.response.ResponseDto;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
public class HealthCheckController {

    @GetMapping("/health-check")
    public ResponseDto<Boolean> healthCheck() {
        return ResponseDto.onSuccess(Boolean.TRUE);
    }

}
