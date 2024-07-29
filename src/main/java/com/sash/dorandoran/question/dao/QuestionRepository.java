package com.sash.dorandoran.question.dao;

import com.sash.dorandoran.question.dao.querydsl.QuestionQueryRepository;
import com.sash.dorandoran.question.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long>, QuestionQueryRepository {
}
