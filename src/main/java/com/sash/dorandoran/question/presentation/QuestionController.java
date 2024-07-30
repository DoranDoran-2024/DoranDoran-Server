package com.sash.dorandoran.question.presentation;

import com.sash.dorandoran.common.annotation.AuthUser;
import com.sash.dorandoran.common.response.ResponseDto;
import com.sash.dorandoran.question.implement.QuestionService;
import com.sash.dorandoran.question.implement.UserAnswerService;
import com.sash.dorandoran.question.presentation.dto.QuestionResponse;
import com.sash.dorandoran.question.presentation.dto.UserAnswerRequest;
import com.sash.dorandoran.question.presentation.dto.UserLevelResponse;
import com.sash.dorandoran.user.domain.User;
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

    @GetMapping
    public ResponseDto<List<QuestionResponse>> getRandomQuestions() {
        return ResponseDto.onSuccess(questionService.getQuestions());
    }

    @PostMapping("/answers")
    public ResponseDto<UserLevelResponse> submitAnswers(@AuthUser User user, @RequestBody List<UserAnswerRequest> request) {
        return ResponseDto.onSuccess(userAnswerService.calculateScoreAndDetermineLevel(user, request));
    }

}
