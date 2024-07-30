package com.sash.dorandoran.lesson.presentation;

import com.sash.dorandoran.common.annotation.AuthUser;
import com.sash.dorandoran.common.response.ResponseDto;
import com.sash.dorandoran.lesson.implement.LessonService;
import com.sash.dorandoran.lesson.presentation.dto.LessonRequest;
import com.sash.dorandoran.user.domain.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/lessons")
@Tag(name = "🏫 Lessoon API")
@RestController
public class LessonController {

    private final LessonService lessonService;

    @PostMapping
    public ResponseDto<Long> createLesson(@AuthUser User user, @RequestBody LessonRequest request) {
        return ResponseDto.onSuccess(lessonService.createLesson(user, request).getId());
    }

}
