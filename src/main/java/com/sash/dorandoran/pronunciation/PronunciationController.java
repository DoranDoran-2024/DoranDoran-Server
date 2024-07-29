package com.sash.dorandoran.pronunciation;

import com.sash.dorandoran.common.response.ResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "🔉 Pronunciation API", description = " API")
@RestController
public class PronunciationController {

    private final PronunciationService pronunciationService;

    @PostMapping("/pronunciation/similarity")
    public ResponseDto<Integer> getSimilarityScore(@RequestBody List<PronunciationRequest> request) {
        return ResponseDto.onSuccess(pronunciationService.getPronunciationSimilarity(request));
    }

}
