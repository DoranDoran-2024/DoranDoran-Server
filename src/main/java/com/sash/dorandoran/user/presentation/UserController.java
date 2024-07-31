package com.sash.dorandoran.user.presentation;

import com.sash.dorandoran.common.annotation.AuthUser;
import com.sash.dorandoran.common.response.ResponseDto;
import com.sash.dorandoran.jwt.JwtProvider;
import com.sash.dorandoran.jwt.JwtResponse;
import com.sash.dorandoran.scrap.implement.ScrapService;
import com.sash.dorandoran.scrap.presentation.dto.ScrapSummaryResponse;
import com.sash.dorandoran.user.business.UserMapper;
import com.sash.dorandoran.user.domain.User;
import com.sash.dorandoran.user.implement.KakaoLoginService;
import com.sash.dorandoran.user.implement.NaverLoginService;
import com.sash.dorandoran.user.implement.UserService;
import com.sash.dorandoran.user.presentation.dto.DiarySummaryListResponse;
import com.sash.dorandoran.user.presentation.dto.JwtRequest;
import com.sash.dorandoran.user.presentation.dto.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "👤 User API")
@RestController
public class UserController {

    private final UserService userService;
    private final ScrapService scrapService;
    private final NaverLoginService naverLoginService;
    private final KakaoLoginService kakaoLoginService;
    private final JwtProvider jwtProvider;

    @Operation(
            summary = "🔑 사용자 정보 조회",
            description = "사용자 정보를 조회합니다."
    )
    @GetMapping("/info")
    public ResponseDto<UserResponse> getUserInfo(@AuthUser User user) {
        List<Boolean> attendanceStatus = userService.getAttendanceStatus(user);
        return ResponseDto.onSuccess(UserMapper.toUserResponse(user, attendanceStatus));
    }

    @Operation(
            summary = "🔑 사용자 출석 저장",
            description = "사용자의 출석 정보를 갱신합니다. 날짜 기준은 API 호출 시점입니다."
    )
    @PostMapping("/attendance")
    public ResponseDto<Boolean> checkAttendance(@AuthUser User user) {
        userService.checkAttendance(user);
        return ResponseDto.onSuccess(true);
    }

    @Operation(
            summary = "네이버 로그인",
            description = "DB에 존재하는 경우 로그인, 존재하지 않는 경우 회원가입을 진행합니다. access token을 반환합니다."
    )
    @PostMapping("/login/naver")
    public ResponseDto<JwtResponse> naverLogin(@RequestBody JwtRequest request) {
        return ResponseDto.onSuccess(naverLoginService.naverLogin(request));
    }

    @Operation(
            summary = "카카오 로그인",
            description = "DB에 존재하는 경우 로그인, 존재하지 않는 경우 회원가입을 진행합니다. access token을 반환합니다."
    )
    @PostMapping("/login/kakao")
    public ResponseDto<JwtResponse> kakaoLogin(@RequestBody JwtRequest request) {
        return ResponseDto.onSuccess(kakaoLoginService.kakaoLogin(request));
    }

    @Operation(
            summary = "🐻 Access Token 발급 (테스트)",
            description = "파라미터의 userId를 가진 사용자의 토큰을 발급합니다."
    )
    @GetMapping("/test")
    public ResponseDto<JwtResponse> generateTestToken(@RequestParam Long userId) {
        return ResponseDto.onSuccess(jwtProvider.generateToken(userService.getUser(userId)));
    }

    @Operation(
            summary = "🔑 [내정보] 사용자 일기 목록 조회",
            description = "사용자의 일기 목록을 조회합니다."
    )
    @GetMapping("/diaries")
    public ResponseDto<DiarySummaryListResponse> getDiaries(@AuthUser User user) {
        return ResponseDto.onSuccess(userService.getDiaries(user));
    }

    @Operation(
            summary = "🔑 [내정보] 사용자 학습 기록 조회",
            description = "사용자의 학습 기록을 조회합니다."
    )
    @GetMapping("/scraps")
    public ResponseDto<List<ScrapSummaryResponse>> getScraps(@AuthUser User user) {
        return ResponseDto.onSuccess(scrapService.getScraps(user));
    }

}
