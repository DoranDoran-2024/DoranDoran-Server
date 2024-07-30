package com.sash.dorandoran.user.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class NicknameGenerator {

    private final List<String> ADJECTIVES = Arrays.asList(
            "아름다운", "고운", "맑은", "푸른", "조용한", "상냥한", "기쁜"
    );

    private final List<String> NOUNS = Arrays.asList(
            "바람", "달빛", "햇살", "별빛", "바다", "산들", "강"
    );

    private final Random RANDOM = new Random();

    public String generateNickname() {
        String adjective = ADJECTIVES.get(RANDOM.nextInt(ADJECTIVES.size()));
        String noun = NOUNS.get(RANDOM.nextInt(NOUNS.size()));
        return adjective + noun;
    }
}
