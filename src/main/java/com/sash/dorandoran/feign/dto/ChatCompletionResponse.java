package com.sash.dorandoran.feign.dto;

import lombok.Data;

import java.util.List;

@Data
public class ChatCompletionResponse {

    private Status status;
    private Result result;

    @Data
    public static class Status {
        private String code;
        private String message;
    }

    @Data
    public static class Result {
        private Message message;
        private int inputLength;
        private int outputLength;
        private String stopReason;
        private long seed;
        private List<AiFilter> aiFilter;
    }

    @Data
    public static class Message {
        private String role;
        private String content;
    }

    @Data
    public static class AiFilter {
        private String groupName;
        private String name;
        private String score;
        private String result;
    }

}
