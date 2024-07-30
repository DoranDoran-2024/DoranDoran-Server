package com.sash.dorandoran.feign.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KakaoUserResponse {
    private Long id;
    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Data
    public static class KakaoAccount {
        private Profile profile;
        private String email;
        @JsonProperty("age_range")
        private String ageRange;
        private String birthday;
        private String gender;

        @Data
        public static class Profile {
            private String nickname;
            @JsonProperty("thumbnail_image_url")
            private String thumbnailImageUrl;
            @JsonProperty("profile_image_url")
            private String profileImageUrl;
        }
    }
}