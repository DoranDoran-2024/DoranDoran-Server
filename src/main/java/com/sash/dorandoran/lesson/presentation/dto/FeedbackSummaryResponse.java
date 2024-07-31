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
public class FeedbackSummaryResponse {

    private List<GradingResponse> grading;
    private Boolean isCorrect;

}
