package com.sash.dorandoran.user.presentation.dto;

import com.sash.dorandoran.user.domain.AuthProvider;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserRequest {

    @Schema(example = "test@example.com")
    private String email;

    @Schema(example = "DEFAULT")
    private AuthProvider authProvider;

    @Schema(example = "도란도란")
    private String nickname;

}
