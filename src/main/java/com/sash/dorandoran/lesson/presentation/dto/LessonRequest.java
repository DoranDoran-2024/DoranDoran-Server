package com.sash.dorandoran.lesson.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LessonRequest {

    @Schema(example = "식당")
    private String bigTopic;

    @Schema(example = "음식 주문하기")
    private String smallTopic;

}
