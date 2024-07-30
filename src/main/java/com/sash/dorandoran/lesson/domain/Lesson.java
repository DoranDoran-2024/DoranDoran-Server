package com.sash.dorandoran.lesson.domain;

import com.sash.dorandoran.common.BaseTimeEntity;
import com.sash.dorandoran.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Lesson extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String situation;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
    private List<Exercise> exercises;

    @Builder
    public Lesson(String situation, User user, List<Exercise> exercises) {
        this.situation = situation;
        this.user = user;
        this.exercises = exercises;
    }
}
