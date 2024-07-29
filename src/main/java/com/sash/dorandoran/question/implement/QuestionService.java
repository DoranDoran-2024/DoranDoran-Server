package com.sash.dorandoran.question.implement;

import com.sash.dorandoran.question.business.QuestionMapper;
import com.sash.dorandoran.question.dao.QuestionRepository;
import com.sash.dorandoran.question.domain.Question;
import com.sash.dorandoran.question.presentation.dto.QuestionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public List<QuestionResponse> getQuestions() {
        return QuestionMapper.toQuestionResponseList(questionRepository.findRandomQuestions());
    }

}
