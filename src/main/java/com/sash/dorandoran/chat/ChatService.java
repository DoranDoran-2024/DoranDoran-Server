package com.sash.dorandoran.chat;

import com.sash.dorandoran.feign.client.ClovaStudioClient;
import com.sash.dorandoran.feign.dto.ChatCompletionRequest;
import com.sash.dorandoran.feign.properties.ChatCompletionProperties;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ClovaStudioClient clovaStudioClient;
    private final ChatCompletionProperties chatCompletionProperties;

    public int getChatCompletion(List<ChatRequest> request) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < request.size(); i++) {
            sb.append(i + 1);
            sb.append(':');
            sb.append(request.get(i).getSpeechText()).append(", ").append(request.get(i).getCorrectText());
            sb.append('\n');
        }

        ChatCompletionRequest.Message systemMessage = ChatCompletionRequest.Message.builder()
                .role("system")
                .content(chatCompletionProperties.getPrompt())
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
