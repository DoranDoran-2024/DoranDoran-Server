package com.sash.dorandoran.feign.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@ToString
@Data
@Builder
public class ChatCompletionRequest {

    private List<Message> messages;
    private double topP;
    private int topK;
    private int maxTokens;
    private double temperature;
    private double repeatPenalty;
    private List<String> stopBefore;
    private boolean includeAiFilters;
    private int seed;

    @ToString
    @Data
    @Builder
    public static class Message {
        private String role;
        private String content;
    }

}
