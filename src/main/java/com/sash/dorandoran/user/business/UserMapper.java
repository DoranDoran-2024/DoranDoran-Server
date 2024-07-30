package com.sash.dorandoran.user.business;

import com.sash.dorandoran.user.domain.User;
import com.sash.dorandoran.user.presentation.dto.UserResponse;

import java.util.List;

public class UserMapper {

    public static UserResponse toUserResponse(User user, List<Boolean> attendanceStatus) {
        int attendanceCount = (int) attendanceStatus.stream().filter(Boolean::booleanValue).count();

        return UserResponse.builder()
                .level(user.getLevel())
                .nickname(user.getNickname())
                .attendanceStatus(attendanceStatus)
                .attendanceCount(attendanceCount)
                .build();
    }

}
