package com.sash.dorandoran.user.domain;

public enum UserLevel {
    NONE("미정"),
    SPROUT("초급"),
    STEM("중급"),
    TREE("고급");

    private final String levelName;

    UserLevel(String levelName) {
        this.levelName = levelName;
    }

    public String getLevelName() {
        return levelName;
    }
}
