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
    private int attendanceCount;

    @Builder
    public UserResponse(UserLevel level, String nickname, List<Boolean> attendanceStatus, int attendanceCount) {
        this.level = level;
        this.nickname = nickname;
        this.attendanceStatus = attendanceStatus;
        this.attendanceCount = attendanceCount;
    }
}
