package com.sash.dorandoran.lesson.presentation;

import com.sash.dorandoran.common.annotation.AuthUser;
import com.sash.dorandoran.common.response.ResponseDto;
import com.sash.dorandoran.lesson.implement.ClovaVoiceService;
import com.sash.dorandoran.lesson.implement.FeedbackService;
import com.sash.dorandoran.lesson.implement.LessonService;
import com.sash.dorandoran.lesson.presentation.dto.*;
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
    private final ClovaVoiceService clovaVoiceService;

    @PostMapping
    public ResponseDto<ExerciseListResponse> createLesson(@AuthUser User user, @RequestBody LessonRequest request) {
        return ResponseDto.onSuccess(lessonService.createLesson(user, request));
    }

    @GetMapping("/{lessonId}")
    public ResponseDto<LessonResponse> getLesson(@AuthUser User user, @PathVariable Long lessonId) {
        return ResponseDto.onSuccess(lessonService.getLesson(user, lessonId));
    }

    @PostMapping("/exercises/{exerciseId}")
    public ResponseDto<FeedbackResponse> createFeedback(@AuthUser User user, @PathVariable Long exerciseId, @RequestBody FeedbackRequest request) {
        return ResponseDto.onSuccess(feedbackService.createFeedback(user, exerciseId, request));
    }

    @PostMapping("/exercises/{exerciseId}/voices")
    public ResponseDto<String> synthesizeAndUpload(@AuthUser User user, @PathVariable Long exerciseId) {
        return ResponseDto.onSuccess(clovaVoiceService.synthesizeSpeechAndUpload(exerciseId));
    }
}
