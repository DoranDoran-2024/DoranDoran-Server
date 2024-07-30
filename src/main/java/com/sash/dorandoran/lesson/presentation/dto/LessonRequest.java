package com.sash.dorandoran.lesson.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LessonRequest {

    @Schema(example = "음식에 대한 불만 표현하기")
    private String situation;

}
