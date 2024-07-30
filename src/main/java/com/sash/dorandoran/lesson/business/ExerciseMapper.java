package com.sash.dorandoran.lesson.business;

import com.sash.dorandoran.lesson.presentation.dto.ExerciseListResponse;

import java.util.List;

public class ExerciseMapper {

    public static ExerciseListResponse toExerciseListResponse(Long lessonId, List<String> exerciseTexts) {
        return ExerciseListResponse.builder()
                .lessonId(lessonId)
                .exerciseTexts(exerciseTexts)
                .build();
    }

}
