package com.sash.dorandoran.lesson.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LessonResponse {

    private int avgScore;
    private List<FeedbackSummaryResponse> feedbacks;
    private List<ExerciseSummaryResponse> incorrectExercises;

}
