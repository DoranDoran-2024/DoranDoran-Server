package com.sash.dorandoran.lesson.domain;

import com.sash.dorandoran.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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

    @Builder
    public Exercise(String exerciseText, String answerText, String answerAudioUrl, String feedback, int score, Lesson lesson) {
        this.exerciseText = exerciseText;
        this.answerText = answerText;
        this.answerAudioUrl = answerAudioUrl;
        this.feedback = feedback;
        this.score = score;
        this.lesson = lesson;
    }
}