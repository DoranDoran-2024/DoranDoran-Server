package com.sash.dorandoran.scrap.implement;


import com.sash.dorandoran.common.exception.GeneralException;
import com.sash.dorandoran.common.response.status.ErrorStatus;
import com.sash.dorandoran.lesson.dao.ExerciseRepository;
import com.sash.dorandoran.lesson.domain.Exercise;
import com.sash.dorandoran.scrap.business.ScrapMapper;
import com.sash.dorandoran.scrap.dao.ScrapRepository;
import com.sash.dorandoran.scrap.domain.Scrap;
import com.sash.dorandoran.scrap.presentation.dto.ScrapSummaryResponse;
import com.sash.dorandoran.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ScrapService {

    private final ScrapRepository scrapRepository;
    private final ExerciseRepository exerciseRepository;

    @Transactional
    public boolean handleScrap(User user, Long exerciseId) {
        Optional<Scrap> optionalScrap = scrapRepository.findScrapByUserAndExerciseId(user, exerciseId);
        if (optionalScrap.isPresent()) {
            scrapRepository.delete(optionalScrap.get());
        } else {
            Exercise exercise = exerciseRepository.findById(exerciseId)
                    .orElseThrow(() -> new GeneralException(ErrorStatus.EXERCISE_NOT_FOUND));
            Scrap scrap = buildScrap(user, exercise);
            scrapRepository.save(scrap);
        }
        return true;
    }

    public List<ScrapSummaryResponse> getScraps(User user) {
        return scrapRepository.findScrapsByUser(user).stream()
                .map(ScrapMapper::toScrapSummaryResponse).toList();
    }

    private Scrap buildScrap(User user, Exercise exercise) {
        return Scrap.builder()
                .user(user)
                .exercise(exercise)
                .build();
    }

}
