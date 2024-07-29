package com.sash.dorandoran.user.presentation.dto;

import com.sash.dorandoran.user.domain.UserLevel;
import lombok.Builder;
import lombok.Data;

@Data
public class UserResponse {

    private UserLevel level;

    @Builder
    public UserResponse(UserLevel level) {
        this.level = level;
    }
}
