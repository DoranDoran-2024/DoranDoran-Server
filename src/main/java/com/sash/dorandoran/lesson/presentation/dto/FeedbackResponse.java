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
public class FeedbackResponse {

    private int score;
    private String feedback;
    private List<GradingResponse> grading;

}
