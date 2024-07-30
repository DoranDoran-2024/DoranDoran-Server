package com.sash.dorandoran.question.implement;

import com.sash.dorandoran.common.exception.GeneralException;
import com.sash.dorandoran.common.response.status.ErrorStatus;
import com.sash.dorandoran.question.business.QuestionMapper;
import com.sash.dorandoran.question.dao.QuestionRepository;
import com.sash.dorandoran.question.domain.Question;
import com.sash.dorandoran.question.presentation.dto.UserAnswerRequest;
import com.sash.dorandoran.question.presentation.dto.UserLevelResponse;
import com.sash.dorandoran.user.domain.User;
import com.sash.dorandoran.user.domain.UserLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserAnswerService {

    private final QuestionRepository questionRepository;

    @Transactional
    public UserLevelResponse calculateScoreAndDetermineLevel(User user, List<UserAnswerRequest> request) {
        int score = calculateScore(request);
        UserLevel level = determineUserLevel(score);
        user.setLevel(level);
        return QuestionMapper.toUserLevelResponse(score, level);
    }

    private int calculateScore(List<UserAnswerRequest> request) {
        int totalScore = 0;

        for (UserAnswerRequest userAnswer : request) {
            Question question = questionRepository.findById(userAnswer.getQuestionId()).orElse(null);

            if (question != null && question.getContent().equals(userAnswer.getAnswer())) {
                switch (question.getDifficulty()) {
                    case EASY:
                        totalScore += 1;
                        break;
                    case MEDIUM:
                        totalScore += 2;
                        break;
                    case HARD:
                        totalScore += 3;
                        break;
                }
            }
        }

        return totalScore;
    }

    private UserLevel determineUserLevel(int score) {
        if (score >= 0 && score <= 2) {
            return UserLevel.SPROUT;
        } else if (score >= 3 && score <= 5) {
            return UserLevel.STEM;
        } else if (score >= 6 && score <= 12) {
            return UserLevel.TREE;
        } else {
            throw new GeneralException(ErrorStatus.BAD_REQUEST);
        }
    }

}
