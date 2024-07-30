package com.sash.dorandoran.lesson.dao;

import com.sash.dorandoran.lesson.domain.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
}
