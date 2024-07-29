package com.sash.dorandoran.question.presentation.dto;

import com.sash.dorandoran.question.domain.QuestionDifficulty;
import lombok.Builder;
import lombok.Data;

@Data
public class QuestionResponse {

    private Long id;
    private String content;
    private QuestionDifficulty difficulty;

    @Builder
    public QuestionResponse(String content, QuestionDifficulty difficulty, Long id) {
        this.content = content;
        this.difficulty = difficulty;
        this.id = id;
    }
}
