package com.sash.dorandoran.lesson.dao;

import com.sash.dorandoran.lesson.domain.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    List<Lesson> findByUserId(Long userId);

}
