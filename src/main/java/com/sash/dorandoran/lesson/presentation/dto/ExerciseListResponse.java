package com.sash.dorandoran.lesson.presentation.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class ExerciseListResponse {

    Long lessonId;
    List<String> exerciseTexts;

    @Builder
    public ExerciseListResponse(Long lessonId, List<String> exerciseTexts) {
        this.lessonId = lessonId;
        this.exerciseTexts = exerciseTexts;
    }
}
