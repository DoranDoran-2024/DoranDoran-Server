package com.sash.dorandoran.question.presentation;

import com.sash.dorandoran.common.response.ResponseDto;
import com.sash.dorandoran.question.implement.QuestionService;
import com.sash.dorandoran.question.presentation.dto.QuestionResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/questions")
@Tag(name = "❓ Question API")
@RestController
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping
    public ResponseDto<List<QuestionResponse>> getRandomQuestions() {
        return ResponseDto.onSuccess(questionService.getQuestions());
    }

}
