package com.sash.dorandoran.user.implement;

import com.sash.dorandoran.feign.client.KakaoUserInfoClient;
import com.sash.dorandoran.feign.dto.KakaoUserResponse;
import com.sash.dorandoran.jwt.JwtProvider;
import com.sash.dorandoran.jwt.JwtResponse;
import com.sash.dorandoran.user.dao.UserRepository;
import com.sash.dorandoran.user.domain.AuthProvider;
import com.sash.dorandoran.user.domain.Role;
import com.sash.dorandoran.user.domain.User;
import com.sash.dorandoran.user.domain.UserLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class KakaoLoginService {

    private final KakaoUserInfoClient kakaoUserInfoClient;
    private final UserRepository userRepository;
    private final NicknameGenerator nicknameGenerator;
    private final JwtProvider jwtProvider;

    @Transactional
    public JwtResponse kakaoLogin(String accessToken) {
        KakaoUserResponse.KakaoAccount profile = requestProfile(accessToken);
        Optional<User> optionalUser = userRepository.findByAuthProviderAndEmail(AuthProvider.NAVER, profile.getEmail());
        User user = optionalUser.orElseGet(() -> userRepository.save(buildUser(profile)));
        return jwtProvider.generateToken(user);
    }

    private KakaoUserResponse.KakaoAccount requestProfile(String accessToken) {
        KakaoUserResponse response = kakaoUserInfoClient.getUserInfo("Bearer " + accessToken,
                "application/x-www-form-urlencoded;charset=utf-8",
                true,
                "[\"kakao_account.email\", \"kakao_account.profile\", \"kakao_account.name\", \"kakao_account.age_range\", \"kakao_account.birthday\", \"kakao_account.gender\"]");
        return response.getKakaoAccount();
    }

    private User buildUser(KakaoUserResponse.KakaoAccount profile) {
        String nickname = profile.getProfile().getNickname();
        if (nickname == null || nickname.trim().isEmpty()) {
            nickname = nicknameGenerator.generateNickname();
        }

        return User.builder()
                .email(profile.getEmail())
                .nickname(nickname)
                .role(Role.MEMBER)
                .level(UserLevel.NONE)
                .authProvider(AuthProvider.KAKAO)
                .build();
    }

}
