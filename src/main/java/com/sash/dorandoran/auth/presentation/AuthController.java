package com.sash.dorandoran.auth.presentation;

import com.sash.dorandoran.auth.implement.NaverLoginService;
import com.sash.dorandoran.common.response.ResponseDto;
import com.sash.dorandoran.jwt.JwtProvider;
import com.sash.dorandoran.jwt.JwtResponse;
import com.sash.dorandoran.user.domain.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "🔒 Auth API")
@RestController
public class AuthController {

    private final NaverLoginService naverLoginService;
    private final JwtProvider jwtProvider;

    @GetMapping("/callback/naver")
    public ResponseDto<JwtResponse> naverCallback(@RequestParam String code) {
        User user = naverLoginService.naverLogin(code);
        return ResponseDto.onSuccess(jwtProvider.generateToken(user));
    }

}
