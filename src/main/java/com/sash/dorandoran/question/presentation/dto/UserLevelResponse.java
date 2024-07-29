package com.sash.dorandoran.question.presentation.dto;

import com.sash.dorandoran.user.domain.UserLevel;
import lombok.Builder;
import lombok.Data;

@Data
public class UserLevelResponse {

    private int score;
    private UserLevel level;

    @Builder
    public UserLevelResponse(int score, UserLevel level) {
        this.score = score;
        this.level = level;
    }
}
