package com.sash.dorandoran.scrap.domain;

import com.sash.dorandoran.common.BaseTimeEntity;
import com.sash.dorandoran.lesson.domain.Exercise;
import com.sash.dorandoran.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Scrap extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    @Builder
    public Scrap(User user, Exercise exercise) {
        this.user = user;
        this.exercise = exercise;
    }
}