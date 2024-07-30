package com.sash.dorandoran.user.presentation;

import com.sash.dorandoran.auth.AuthUser;
import com.sash.dorandoran.common.response.ResponseDto;
import com.sash.dorandoran.jwt.JwtResponse;
import com.sash.dorandoran.user.business.UserMapper;
import com.sash.dorandoran.user.domain.User;
import com.sash.dorandoran.user.implement.UserService;
import com.sash.dorandoran.user.presentation.dto.SignInRequest;
import com.sash.dorandoran.user.presentation.dto.SignUpRequest;
import com.sash.dorandoran.user.presentation.dto.UserResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "👤 User API")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseDto<JwtResponse> signUp(@RequestBody SignUpRequest request) {
        return ResponseDto.onSuccess(userService.signUp(request));
    }

    @PostMapping("/sign-in")
    public ResponseDto<JwtResponse> signIn(@RequestBody SignInRequest request) {
        return ResponseDto.onSuccess(userService.signIn(request));
    }

    @GetMapping("/info")
    public ResponseDto<UserResponse> getUserInfo(@AuthUser User user) {
        return ResponseDto.onSuccess(UserMapper.toUserResponse(user));
    }

}
