package com.sash.dorandoran.lesson.business;

import com.sash.dorandoran.lesson.domain.Exercise;
import com.sash.dorandoran.lesson.presentation.dto.ExerciseListResponse;
import com.sash.dorandoran.lesson.presentation.dto.ExerciseSummaryResponse;

import java.util.List;
import java.util.stream.Collectors;

public class ExerciseMapper {

    public static ExerciseSummaryResponse toExerciseSummaryResponse(Exercise exercise) {
        return ExerciseSummaryResponse.builder()
                .exerciseId(exercise.getId())
                .exerciseText(exercise.getExerciseText())
                .build();
    }

    public static ExerciseListResponse toExerciseListResponse(Long lessonId, List<Exercise> exercises) {
        List<ExerciseSummaryResponse> exerciseSummaries = exercises.stream()
                .map(ExerciseMapper::toExerciseSummaryResponse)
                .collect(Collectors.toList());

        return ExerciseListResponse.builder()
                .lessonId(lessonId)
                .exercises(exerciseSummaries)
                .build();
    }
}
