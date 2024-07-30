package com.sash.dorandoran.user.presentation.dto;

import com.sash.dorandoran.user.domain.UserLevel;
import lombok.Builder;
import lombok.Data;

@Data
public class UserResponse {

    private UserLevel level;

    private String nickname;

    @Builder
    public UserResponse(UserLevel level, String nickname) {
        this.level = level;
        this.nickname = nickname;
    }
}
