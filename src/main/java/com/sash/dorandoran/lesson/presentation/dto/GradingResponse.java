package com.sash.dorandoran.lesson.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GradingResponse {

    private String incorrectWord;
    private String correctWord;

}
