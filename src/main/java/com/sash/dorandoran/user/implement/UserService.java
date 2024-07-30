package com.sash.dorandoran.user.implement;

import com.sash.dorandoran.auth.implement.NaverLoginService;
import com.sash.dorandoran.common.exception.GeneralException;
import com.sash.dorandoran.common.response.status.ErrorStatus;
import com.sash.dorandoran.jwt.JwtProvider;
import com.sash.dorandoran.jwt.JwtResponse;
import com.sash.dorandoran.user.dao.AttendanceRepository;
import com.sash.dorandoran.user.dao.UserRepository;
import com.sash.dorandoran.user.domain.*;
import com.sash.dorandoran.user.presentation.dto.SignInRequest;
import com.sash.dorandoran.user.presentation.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.IntStream;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final AttendanceRepository attendanceRepository;
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

    @Transactional
    public void checkAttendance(User user) {
        LocalDate today = LocalDate.now();
        if (attendanceRepository.findByUserAndDateBetween(user, today, today).isEmpty()) {
            Attendance attendance = Attendance.builder()
                    .user(user)
                    .date(today)
                    .build();
            attendanceRepository.save(attendance);
        }
    }

    public List<Boolean> getAttendanceStatus(User user) {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        LocalDate endOfWeek = today.with(TemporalAdjusters.nextOrSame(java.time.DayOfWeek.SUNDAY));

        List<LocalDate> attendedDates = attendanceRepository.findByUserAndDateBetween(user, startOfWeek, endOfWeek)
                .stream()
                .map(Attendance::getDate)
                .toList();

        return IntStream.range(0, 7)
                .mapToObj(i -> attendedDates.contains(startOfWeek.plusDays(i)))
                .toList();
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

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
    }

}
