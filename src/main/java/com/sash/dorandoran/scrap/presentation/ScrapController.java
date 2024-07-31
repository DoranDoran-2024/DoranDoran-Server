package com.sash.dorandoran.scrap.presentation;

import com.sash.dorandoran.common.annotation.AuthUser;
import com.sash.dorandoran.common.response.ResponseDto;
import com.sash.dorandoran.scrap.implement.ScrapService;
import com.sash.dorandoran.scrap.presentation.dto.ScrapRequest;
import com.sash.dorandoran.scrap.presentation.dto.ScrapSummaryResponse;
import com.sash.dorandoran.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/scraps")
@Tag(name = "📂 Scrap API")
@RestController
public class ScrapController {

    private final ScrapService scrapService;

    @Operation(
            summary = "🔑 [실전 학습 - 분석 결과] 문제 스크랩",
            description = "실전 학습에서 틀린 문제를 스크랩합니다."
    )
    @PostMapping
    public ResponseDto<Boolean> handleScrap(@AuthUser User user, @RequestBody ScrapRequest request) {
        return ResponseDto.onSuccess(scrapService.createScraps(user, request));
    }

    @Operation(
            summary = "🔑 [내정보 - 학습 기록] 스크랩 목록 조회",
            description = "실전 학습에서 스크랩한 문제 목록을 조회합니다. (페이징 X)"
    )
    @GetMapping
    public ResponseDto<List<ScrapSummaryResponse>> getScraps(@AuthUser User user) {
        return ResponseDto.onSuccess(scrapService.getScraps(user));
    }


}
