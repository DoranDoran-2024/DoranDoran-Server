package com.sash.dorandoran.lesson.business;

import com.sash.dorandoran.lesson.domain.Lesson;
import com.sash.dorandoran.lesson.domain.Exercise;
import com.sash.dorandoran.lesson.presentation.dto.ExerciseSummaryResponse;
import com.sash.dorandoran.lesson.presentation.dto.FeedbackSummaryResponse;
import com.sash.dorandoran.lesson.presentation.dto.GradingResponse;
import com.sash.dorandoran.lesson.presentation.dto.LessonResponse;

import java.util.List;
import java.util.stream.Collectors;

public class LessonMapper {

    public static LessonResponse toLessonResponse(Lesson lesson) {
        List<Exercise> exercises = lesson.getExercises();

        int avgScore = exercises.stream().mapToInt(Exercise::getScore).sum() / exercises.size();

        List<ExerciseSummaryResponse> incorrectExercises = exercises.stream()
                .filter(exercise -> exercise.getScore() < 100)
                .map(exercise -> ExerciseSummaryResponse.builder()
                        .exerciseId(exercise.getId())
                        .correctText(exercise.getCorrectText())
                        .build())
                .collect(Collectors.toList());

        List<FeedbackSummaryResponse> feedbacks = exercises.stream()
                .map(exercise -> FeedbackSummaryResponse.builder()
                        .grading(exercise.getGrading().stream()
                                .map(grading -> GradingResponse.builder()
                                        .incorrectWord(grading.getIncorrectWord())
                                        .correctWord(grading.getCorrectWord())
                                        .build())
                                .collect(Collectors.toList()))
                        .isCorrect(exercise.getGrading().isEmpty() || exercise.getScore() == 100)
                        .build())
                .collect(Collectors.toList());

        return LessonResponse.builder()
                .avgScore(avgScore)
                .feedbacks(feedbacks)
                .incorrectExercises(incorrectExercises)
                .build();
    }
}
