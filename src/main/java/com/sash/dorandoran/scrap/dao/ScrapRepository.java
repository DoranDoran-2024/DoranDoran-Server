package com.sash.dorandoran.scrap.dao;

import com.sash.dorandoran.scrap.domain.Scrap;
import com.sash.dorandoran.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {

    Optional<Scrap> findScrapByUserAndExerciseId(User user, Long exerciseId);
    List<Scrap> findScrapsByUser(User user);

}
