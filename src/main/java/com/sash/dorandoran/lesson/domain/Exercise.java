package com.sash.dorandoran.lesson.domain;

import com.sash.dorandoran.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Exercise extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String exerciseText;
    private String answerText;
    private String answerAudioUrl;
    private String feedback;
    private int score;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

}