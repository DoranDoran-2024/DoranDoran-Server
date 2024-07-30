package com.sash.dorandoran.user.implement;

import com.sash.dorandoran.common.exception.GeneralException;
import com.sash.dorandoran.common.response.status.ErrorStatus;
import com.sash.dorandoran.jwt.JwtProvider;
import com.sash.dorandoran.jwt.JwtResponse;
import com.sash.dorandoran.user.dao.UserRepository;
import com.sash.dorandoran.user.domain.Role;
import com.sash.dorandoran.user.domain.User;
import com.sash.dorandoran.user.domain.UserLevel;
import com.sash.dorandoran.user.presentation.dto.SignInRequest;
import com.sash.dorandoran.user.presentation.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final NicknameGenerator nicknameGenerator;

    @Transactional
    public JwtResponse signUp(SignUpRequest request) {
        if (userRepository.findByAuthProviderAndEmail(request.getAuthProvider(), request.getEmail()).isPresent()) {
            throw new GeneralException(ErrorStatus.USERNAME_DUPLICATED);
        }
        User user = userRepository.save(buildUser(request));
        return jwtProvider.generateToken(user);
    }

    @Transactional
    public JwtResponse signIn(SignInRequest request) {
        User user = userRepository.findByAuthProviderAndEmail(request.getAuthProvider(), request.getEmail())
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
        return jwtProvider.generateToken(user);
    }

    private User buildUser(SignUpRequest request) {
        String nickname = request.getNickname();
        if (nickname == null || nickname.trim().isEmpty()) {
            nickname = nicknameGenerator.generateNickname();
        }

        return User.builder()
                .authProvider(request.getAuthProvider())
                .level(UserLevel.SPROUT)
                .email(request.getEmail())
                .nickname(nickname)
                .role(Role.MEMBER)
                .build();
    }

}
