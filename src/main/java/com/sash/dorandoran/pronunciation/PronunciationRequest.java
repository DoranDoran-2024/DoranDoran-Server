package com.sash.dorandoran.pronunciation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PronunciationRequest {

    @Schema(example = "안녕하세요")
    private String speechText;

    @Schema(example = "안뇽하세여")
    private String correctText;

}
