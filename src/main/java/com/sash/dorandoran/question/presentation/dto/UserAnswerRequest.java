package com.sash.dorandoran.question.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserAnswerRequest {

    @Schema(example = "22")
    private Long questionId;

    @Schema(example = "과학 기술의 발전이 우리의 일상 생활에 미친 영향을 분석해 보세요.")
    private String answer;

}
