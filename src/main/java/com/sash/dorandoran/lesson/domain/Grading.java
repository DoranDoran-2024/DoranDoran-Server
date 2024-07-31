package com.sash.dorandoran.lesson.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Grading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String incorrectWord;
    private String correctWord;

    @Setter
    @ManyToOne
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;
}
