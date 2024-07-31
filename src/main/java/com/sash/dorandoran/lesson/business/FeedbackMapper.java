package com.sash.dorandoran.lesson.business;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sash.dorandoran.feign.dto.ChatCompletionResponse;
import com.sash.dorandoran.lesson.presentation.dto.FeedbackResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@Slf4j
public class FeedbackMapper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static FeedbackResponse toFeedbackResponse(ChatCompletionResponse chatCompletionResponse) {
        log.info("response = {}", chatCompletionResponse);
        ChatCompletionResponse.Result result = chatCompletionResponse.getResult();

        FeedbackResponse feedbackResponse = null;
        try {
            // JSON 파싱
            feedbackResponse = objectMapper.readValue(result.getMessage().getContent(), FeedbackResponse.class);
        } catch (IOException e) {
            log.error("Failed to parse feedback response", e);
            return FeedbackResponse.builder()
                    .score(0)
                    .feedback("올바른 문장을 입력해 주세요!")
                    .grading(List.of())
                    .build();
        }

        return feedbackResponse;
    }
}
