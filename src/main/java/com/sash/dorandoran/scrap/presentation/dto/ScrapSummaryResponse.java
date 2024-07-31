package com.sash.dorandoran.scrap.presentation.dto;

import com.sash.dorandoran.lesson.presentation.dto.ExerciseSummaryResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScrapSummaryResponse {
    
    private Long scrapId;
    private ExerciseSummaryResponse exercise;
    
}
