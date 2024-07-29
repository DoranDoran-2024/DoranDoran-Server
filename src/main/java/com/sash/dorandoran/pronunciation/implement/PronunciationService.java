package com.sash.dorandoran.pronunciation.implement;

import com.sash.dorandoran.feign.client.ClovaStudioClient;
import com.sash.dorandoran.feign.dto.ChatCompletionRequest;
import com.sash.dorandoran.feign.properties.ChatCompletionProperties;
import com.sash.dorandoran.pronunciation.presentation.dto.PronunciationRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class PronunciationService {

    private final ClovaStudioClient clovaStudioClient;
    private final ChatCompletionProperties chatCompletionProperties;

    @Value("${pronunciation-similarity-prompt}")
    private String prompt;

    public int getPronunciationSimilarity(List<PronunciationRequest> request) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < request.size(); i++) {
            sb.append(i + 1);
            sb.append(':');
            sb.append(request.get(i).getSpeechText()).append(", ").append(request.get(i).getCorrectText());
            sb.append('\n');
        }

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

        log.info("chatCompletionRequest = {}", chatCompletionRequest);

        return Integer.parseInt(clovaStudioClient.getChatCompletion(
                chatCompletionProperties.getApiKey(),
                chatCompletionProperties.getApigwKey(),
                chatCompletionProperties.getRequestId(),
                chatCompletionRequest
        ).getResult().getMessage().getContent());
    }

}
