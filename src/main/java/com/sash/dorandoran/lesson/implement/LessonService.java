package com.sash.dorandoran.lesson.implement;

import com.sash.dorandoran.lesson.dao.LessonRepository;
import com.sash.dorandoran.lesson.domain.Lesson;
import com.sash.dorandoran.lesson.presentation.dto.LessonRequest;
import com.sash.dorandoran.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class LessonService {

    private final LessonRepository lessonRepository;

    @Transactional
    public Lesson createLesson(User user, LessonRequest request) {
        Lesson lesson = buildLesson(user, request.getSituation());
        return lessonRepository.save(lesson);
    }

    private Lesson buildLesson(User user, String situation) {
        return Lesson.builder()
                .situation(situation)
                .user(user)
                .exercises(new ArrayList<>())
                .build();
    }

}
