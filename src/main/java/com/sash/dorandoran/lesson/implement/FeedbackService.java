package com.sash.dorandoran.lesson.implement;

import com.sash.dorandoran.common.exception.GeneralException;
import com.sash.dorandoran.common.response.status.ErrorStatus;
import com.sash.dorandoran.feign.client.ClovaStudioClient;
import com.sash.dorandoran.feign.dto.ChatCompletionRequest;
import com.sash.dorandoran.feign.properties.ChatCompletionProperties;
import com.sash.dorandoran.lesson.business.FeedbackMapper;
import com.sash.dorandoran.lesson.dao.ExerciseRepository;
import com.sash.dorandoran.lesson.dao.GradingRepository;
import com.sash.dorandoran.lesson.domain.Exercise;
import com.sash.dorandoran.lesson.domain.Grading;
import com.sash.dorandoran.lesson.presentation.dto.FeedbackResponse;
import com.sash.dorandoran.lesson.presentation.dto.FeedbackRequest;
import com.sash.dorandoran.lesson.presentation.dto.GradingResponse;
import com.sash.dorandoran.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class FeedbackService {

    private final ExerciseRepository exerciseRepository;
    private final GradingRepository gradingRepository;
    private final ClovaStudioClient clovaStudioClient;
    private final ChatCompletionProperties chatCompletionProperties;

    @Value("${feedback-create-prompt}")
    private String prompt;

    @Transactional
    public FeedbackResponse createFeedback(User user, Long exerciseId, FeedbackRequest request) {
        StringBuilder sb = new StringBuilder();

        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
        sb.append(exercise.getCorrectText()).append(", ").append(request.getSpeechText());

        ChatCompletionRequest.Message systemMessage = ChatCompletionRequest.Message.builder()
                .role("system")
                .content(prompt)
                .build();

        ChatCompletionRequest.Message userMessage = ChatCompletionRequest.Message.builder()
                .role("user")
                .content(sb.toString())
                .build();

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .messages(Arrays.asList(systemMessage, userMessage))
                .topP(0.8)
                .topK(0)
                .maxTokens(256)
                .temperature(0.5)
                .repeatPenalty(5.0)
                .stopBefore(List.of())
                .includeAiFilters(true)
                .seed(0)
                .build();

        FeedbackResponse response = FeedbackMapper.toFeedbackResponse(clovaStudioClient.getChatCompletion(
                chatCompletionProperties.getApiKey(),
                chatCompletionProperties.getApigwKey(),
                chatCompletionProperties.getRequestId(),
                chatCompletionRequest), request.getSpeechText(), exercise.getCorrectText());

        exercise.updateFeedbackInfo(request.getSpeechText(), response.getFeedback(), response.getScore());

        for (GradingResponse gradingResponse : response.getGrading()) {
            Grading grading = Grading.builder()
                    .incorrectWord(gradingResponse.getIncorrectWord())
                    .correctWord(gradingResponse.getCorrectWord())
                    .exercise(exercise)
                    .build();
            gradingRepository.save(grading);
            exercise.addGrading(grading);
        }

        exerciseRepository.save(exercise);

        return response;
    }


}
