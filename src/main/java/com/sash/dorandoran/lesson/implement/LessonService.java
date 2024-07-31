package com.sash.dorandoran.lesson.implement;

import com.sash.dorandoran.common.exception.GeneralException;
import com.sash.dorandoran.common.response.status.ErrorStatus;
import com.sash.dorandoran.feign.client.ClovaStudioClient;
import com.sash.dorandoran.feign.dto.ChatCompletionRequest;
import com.sash.dorandoran.feign.properties.ChatCompletionProperties;
import com.sash.dorandoran.lesson.business.ExerciseMapper;
import com.sash.dorandoran.lesson.business.LessonMapper;
import com.sash.dorandoran.lesson.dao.ExerciseRepository;
import com.sash.dorandoran.lesson.dao.LessonRepository;
import com.sash.dorandoran.lesson.domain.Exercise;
import com.sash.dorandoran.lesson.domain.Lesson;
import com.sash.dorandoran.lesson.presentation.dto.ExerciseListResponse;
import com.sash.dorandoran.lesson.presentation.dto.LessonRequest;
import com.sash.dorandoran.lesson.presentation.dto.LessonResponse;
import com.sash.dorandoran.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final ExerciseRepository exerciseRepository;
    private final ClovaStudioClient clovaStudioClient;
    private final ChatCompletionProperties chatCompletionProperties;

    @Value("${exercise-create-prompt}")
    private String prompt;

    @Transactional
    public ExerciseListResponse createLesson(User user, LessonRequest request) {
        Lesson lesson = buildLesson(user, request.getSituation());
        lessonRepository.save(lesson);
        List<Exercise> exercises = new ArrayList<>();
        List<String> exerciseTexts = createExercises(user.getLevel().getLevelName(), request.getSituation());
        for (String exerciseText : exerciseTexts) {
            Exercise exercise = buildExercise(lesson, exerciseText);
            exercises.add(exerciseRepository.save(exercise));
        }
        return ExerciseMapper.toExerciseListResponse(lesson.getId(), exercises);
    }

    private Lesson buildLesson(User user, String situation) {
        return Lesson.builder()
                .situation(situation)
                .user(user)
                .exercises(new ArrayList<>())
                .build();
    }

    private Exercise buildExercise(Lesson lesson, String exerciseText) {
        return Exercise.builder()
                .lesson(lesson)
                .exerciseText(exerciseText)
                .build();
    }

    private List<String> createExercises(String level, String situation) {
        StringBuilder sb = new StringBuilder();
        sb.append(level).append(", ").append(situation);

        ChatCompletionRequest.Message systemMessage = ChatCompletionRequest.Message.builder()
                .role("system")
                .content(prompt)
                .build();

        ChatCompletionRequest.Message userMessage = ChatCompletionRequest.Message.builder()
                .role("user")
                .content(sb.toString())
                .build();

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .messages(Arrays.asList(systemMessage, userMessage))
                .topP(0.8)
                .topK(0)
                .maxTokens(256)
                .temperature(0.5)
                .repeatPenalty(5.0)
                .stopBefore(List.of())
                .includeAiFilters(true)
                .seed(0)
                .build();

        return Arrays.stream(clovaStudioClient.getChatCompletion(
                chatCompletionProperties.getApiKey(),
                chatCompletionProperties.getApigwKey(),
                chatCompletionProperties.getRequestId(),
                chatCompletionRequest
        ).getResult().getMessage().getContent().split("/")).toList();
    }

    public LessonResponse getLesson(User user, Long lessonId) {
        return LessonMapper.toLessonResponse(lessonRepository.findById(lessonId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.LESSON_NOT_FOUND)));

    }
}
