package com.sash.dorandoran.question.dao.querydsl;

import com.sash.dorandoran.question.domain.Question;

import java.util.List;

public interface QuestionQueryRepository {

    List<Question> findRandomQuestions();

}
