package com.sash.dorandoran.scrap.presentation;

import com.sash.dorandoran.common.annotation.AuthUser;
import com.sash.dorandoran.common.response.ResponseDto;
import com.sash.dorandoran.scrap.implement.ScrapService;
import com.sash.dorandoran.scrap.presentation.dto.ScrapRequest;
import com.sash.dorandoran.scrap.presentation.dto.ScrapSummaryResponse;
import com.sash.dorandoran.user.domain.User;
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

    @PostMapping
    public ResponseDto<Boolean> handleScrap(@AuthUser User user, @RequestBody ScrapRequest request) {
        return ResponseDto.onSuccess(scrapService.createScraps(user, request));
    }

    @GetMapping
    public ResponseDto<List<ScrapSummaryResponse>> getScraps(@AuthUser User user) {
        return ResponseDto.onSuccess(scrapService.getScraps(user));
    }


}
