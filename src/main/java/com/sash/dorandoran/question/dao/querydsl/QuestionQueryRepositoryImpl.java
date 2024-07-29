package com.sash.dorandoran.question.dao.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sash.dorandoran.question.domain.Question;
import com.sash.dorandoran.question.domain.QuestionDifficulty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sash.dorandoran.question.domain.QQuestion.question;

@RequiredArgsConstructor
@Repository
public class QuestionQueryRepositoryImpl implements QuestionQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<Question> findRandomQuestions() {
        List<Question> easyQuestions = findRandomQuestionsByDifficulty(QuestionDifficulty.EASY, 2);
        List<Question> mediumQuestions = findRandomQuestionsByDifficulty(QuestionDifficulty.MEDIUM, 2);
        List<Question> hardQuestions = findRandomQuestionsByDifficulty(QuestionDifficulty.HARD, 2);

        easyQuestions.addAll(mediumQuestions);
        easyQuestions.addAll(hardQuestions);

        return easyQuestions;
    }

    private List<Question> findRandomQuestionsByDifficulty(QuestionDifficulty difficulty, int count) {
        return queryFactory
                .selectFrom(question)
                .where(question.difficulty.eq(difficulty))
                .orderBy(com.querydsl.core.types.dsl.Expressions.numberTemplate(Double.class, "function('rand')").asc())
                .limit(count)
                .fetch();
    }

}
