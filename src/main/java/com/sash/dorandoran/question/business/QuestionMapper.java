package com.sash.dorandoran.question.business;

import com.sash.dorandoran.question.domain.Question;
import com.sash.dorandoran.question.presentation.dto.QuestionResponse;

import java.util.List;
import java.util.stream.Collectors;

public class QuestionMapper {

    public static QuestionResponse toQuestionResponse(Question question) {
        return QuestionResponse.builder()
                .id(question.getId())
                .content(question.getContent())
                .difficulty(question.getDifficulty())
                .build();
    }

    public static List<QuestionResponse> toQuestionResponseList(List<Question> questions) {
        return questions.stream()
                .map(QuestionMapper::toQuestionResponse)
                .collect(Collectors.toList());
    }

}
