package com.sash.dorandoran.user.business;

import com.sash.dorandoran.user.domain.User;
import com.sash.dorandoran.user.presentation.dto.UserResponse;

public class UserMapper {

    public static UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .level(user.getLevel())
                .nickname(user.getNickname())
                .build();
    }

}
