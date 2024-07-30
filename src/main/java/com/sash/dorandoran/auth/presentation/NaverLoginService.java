package com.sash.dorandoran.auth.presentation;

import com.sash.dorandoran.feign.client.NaverRequestTokenClient;
import com.sash.dorandoran.feign.client.NaverUserInfoClient;
import com.sash.dorandoran.feign.dto.NaverTokenResponse;
import com.sash.dorandoran.feign.dto.NaverUserResponse;
import com.sash.dorandoran.feign.properties.NaverProperties;
import com.sash.dorandoran.user.dao.UserRepository;
import com.sash.dorandoran.user.domain.AuthProvider;
import com.sash.dorandoran.user.domain.Role;
import com.sash.dorandoran.user.domain.User;
import com.sash.dorandoran.user.domain.UserLevel;
import com.sash.dorandoran.user.implement.NicknameGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class NaverLoginService {

    private final NaverRequestTokenClient naverRequestTokenClient;
    private final NaverUserInfoClient naverUserInfoClient;
    private final NaverProperties naverProperties;
    private final UserRepository userRepository;
    private final NicknameGenerator nicknameGenerator;

    @Transactional
    public User naverLogin(String code) {
        String accessToken = requestAccessToken(code);
        NaverUserResponse.NaverUserDetail profile = requestProfile(accessToken);

        log.info("profile = {}", profile);
        Optional<User> optionalUser = userRepository.findByAuthProviderAndEmail(AuthProvider.NAVER, profile.getEmail());
        return optionalUser.orElseGet(() -> {
            return userRepository.save(buildUser(profile));
        });
    }

    private User buildUser(NaverUserResponse.NaverUserDetail profile) {
        String nickname = profile.getNickname();
        if (nickname == null || nickname.trim().isEmpty()) {
            nickname = nicknameGenerator.generateNickname();
        }

        return User.builder()
                .email(profile.getEmail())
                .nickname(nickname)
                .role(Role.MEMBER)
                .level(UserLevel.NONE)
                .authProvider(AuthProvider.NAVER)
                .build();
    }

    private String requestAccessToken(String code) {
        Map<String, String> form = new HashMap<>();
        form.put("grant_type", "authorization_code");
        form.put("client_id", naverProperties.getClientId());
        form.put("client_secret", naverProperties.getClientSecret());
        form.put("code", code);
        form.put("redirect_uri", naverProperties.getRedirectUri());

        NaverTokenResponse response = naverRequestTokenClient.getToken(form);
        return response.getAccessToken();
    }

    private NaverUserResponse.NaverUserDetail requestProfile(String accessToken) {
        NaverUserResponse response = naverUserInfoClient.getUserInfo("Bearer " + accessToken);
        return response.getNaverUserDetail();
    }

}
