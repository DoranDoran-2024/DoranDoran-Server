package com.sash.dorandoran.user.presentation.dto;

import com.sash.dorandoran.user.domain.UserLevel;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class UserResponse {

    private UserLevel level;
    private String nickname;
    private List<Boolean> attendanceStatus;

    @Builder
    public UserResponse(UserLevel level, String nickname, List<Boolean> attendanceStatus) {
        this.level = level;
        this.nickname = nickname;
        this.attendanceStatus = attendanceStatus;
    }
}
