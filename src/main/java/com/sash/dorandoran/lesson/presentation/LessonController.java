package com.sash.dorandoran.lesson.presentation;

import com.sash.dorandoran.common.annotation.AuthUser;
import com.sash.dorandoran.common.response.ResponseDto;
import com.sash.dorandoran.lesson.implement.ClovaVoiceService;
import com.sash.dorandoran.lesson.implement.FeedbackService;
import com.sash.dorandoran.lesson.implement.LessonService;
import com.sash.dorandoran.lesson.presentation.dto.*;
import com.sash.dorandoran.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(
            summary = "🔑 [실전 학습] 수업 생성 및 질문 4개 생성",
            description = "오늘의 수업과 질문을 생성합니다. 응답으로 4개의 질문 정보를 반환합니다."
    )
    @PostMapping
    public ResponseDto<ExerciseListResponse> createLesson(@AuthUser User user, @RequestBody LessonRequest request) {
        return ResponseDto.onSuccess(lessonService.createLesson(user, request));
    }

    @Operation(
            summary = "🔑 [실전 학습] 수업 결과 조회",
            description = "오늘의 수업 결과를 조회합니다."
    )
    @GetMapping("/{lessonId}")
    public ResponseDto<LessonResponse> getLesson(@AuthUser User user, @PathVariable Long lessonId) {
        return ResponseDto.onSuccess(lessonService.getLesson(user, lessonId));
    }

    @Operation(
            summary = "🔑 [실전 학습] 수업 결과 조회",
            description = "오늘의 수업 결과를 조회합니다."
    )
    @PostMapping("/exercises/{exerciseId}")
    public ResponseDto<FeedbackResponse> createFeedback(@AuthUser User user, @PathVariable Long exerciseId, @RequestBody FeedbackRequest request) {
        return ResponseDto.onSuccess(feedbackService.createFeedback(user, exerciseId, request));
    }

    @Operation(
            summary = "🔑 [실전 학습] 문제 음성 파일 생성",
            description = "문제에 대한 음성 파일을 생성합니다. 파일 포맷은 wav입니다."
    )
    @PostMapping("/exercises/{exerciseId}/voices")
    public ResponseDto<String> synthesizeAndUpload(@AuthUser User user, @PathVariable Long exerciseId) {
        return ResponseDto.onSuccess(clovaVoiceService.synthesizeSpeechAndUpload(exerciseId));
    }

}
