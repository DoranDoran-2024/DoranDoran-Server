package com.sash.dorandoran.lesson.dao;

import com.sash.dorandoran.lesson.domain.Grading;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradingRepository extends JpaRepository<Grading, Long> {
}
