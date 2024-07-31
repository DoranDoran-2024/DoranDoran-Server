package com.sash.dorandoran.question.presentation;

import com.sash.dorandoran.common.annotation.AuthUser;
import com.sash.dorandoran.common.response.ResponseDto;
import com.sash.dorandoran.question.implement.QuestionService;
import com.sash.dorandoran.question.implement.UserAnswerService;
import com.sash.dorandoran.question.presentation.dto.QuestionResponse;
import com.sash.dorandoran.question.presentation.dto.UserAnswerRequest;
import com.sash.dorandoran.question.presentation.dto.UserLevelResponse;
import com.sash.dorandoran.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/questions")
@Tag(name = "❓ Question API")
@RestController
public class QuestionController {

    private final QuestionService questionService;
    private final UserAnswerService userAnswerService;

    @Operation(
            summary = "[온보딩] 테스트 질문 4개 생성",
            description = "온보딩에서 발음 유사성을 측정하기 위한 테스트 질문을 4개 셍성합니다."
    )
    @GetMapping
    public ResponseDto<List<QuestionResponse>> getRandomQuestions() {
        return ResponseDto.onSuccess(questionService.getQuestions());
    }

    @Operation(
            summary = "🔑 [온보딩] 테스트 질문 답변 제출",
            description = "온보딩에서 테스트 질문에 대한 답변을 제출하고, 사용자 레벨을 반환합니다. (SPROUT, STEM, TREE)"
    )
    @PostMapping("/answers")
    public ResponseDto<UserLevelResponse> submitAnswers(@AuthUser User user, @RequestBody List<UserAnswerRequest> request) {
        return ResponseDto.onSuccess(userAnswerService.calculateScoreAndDetermineLevel(user, request));
    }

}
