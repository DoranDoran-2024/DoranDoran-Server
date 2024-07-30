package com.sash.dorandoran.lesson.dao;

import com.sash.dorandoran.lesson.domain.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
}
