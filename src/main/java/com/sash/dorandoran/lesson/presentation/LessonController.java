package com.sash.dorandoran.lesson.presentation;

import com.sash.dorandoran.common.annotation.AuthUser;
import com.sash.dorandoran.common.response.ResponseDto;
import com.sash.dorandoran.lesson.implement.FeedbackService;
import com.sash.dorandoran.lesson.implement.LessonService;
import com.sash.dorandoran.lesson.presentation.dto.ExerciseListResponse;
import com.sash.dorandoran.lesson.presentation.dto.FeedbackRequest;
import com.sash.dorandoran.lesson.presentation.dto.FeedbackResponse;
import com.sash.dorandoran.lesson.presentation.dto.LessonRequest;
import com.sash.dorandoran.user.domain.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/lessons")
@Tag(name = "🏫 Lesson API")
@RestController
public class LessonController {

    private final LessonService lessonService;
    private final FeedbackService feedbackService;

    @PostMapping
    public ResponseDto<ExerciseListResponse> createLesson(@AuthUser User user, @RequestBody LessonRequest request) {
        return ResponseDto.onSuccess(lessonService.createLesson(user, request));
    }

    @PostMapping("/exercises/{exerciseId}")
    public ResponseDto<FeedbackResponse> createFeedback(@AuthUser User user, @PathVariable Long exerciseId, @RequestBody FeedbackRequest request) {
        return ResponseDto.onSuccess(feedbackService.createFeedback(user, exerciseId, request));
    }

}
