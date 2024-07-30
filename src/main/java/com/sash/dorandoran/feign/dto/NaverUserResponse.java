package com.sash.dorandoran.feign.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NaverUserResponse {

    @JsonProperty("resultcode")
    private String resultCode;

    @JsonProperty("message")
    private String message;

    @JsonProperty("response")
    private NaverUserDetail naverUserDetail;

    @Data
    public static class NaverUserDetail {
        private String id;
        private String nickname;
        private String email;
    }
}