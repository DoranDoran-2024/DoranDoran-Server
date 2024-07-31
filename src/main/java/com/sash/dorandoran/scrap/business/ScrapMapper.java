package com.sash.dorandoran.scrap.business;

import com.sash.dorandoran.scrap.domain.Scrap;
import com.sash.dorandoran.scrap.presentation.dto.ScrapSummaryResponse;
import com.sash.dorandoran.lesson.business.ExerciseMapper;

public class ScrapMapper {

    public static ScrapSummaryResponse toScrapSummaryResponse(Scrap scrap) {
        return ScrapSummaryResponse.builder()
                .scrapId(scrap.getId())
                .exercise(ExerciseMapper.toExerciseSummaryResponse(scrap.getExercise()))
                .bigTopic(scrap.getExercise().getLesson().getSituation())
                .build();
    }
}
