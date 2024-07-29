package com.sash.dorandoran.question.presentation.dto;

import lombok.Data;

@Data
public class UserAnswerRequest {

    private Long questionId;
    private String answer;

}
