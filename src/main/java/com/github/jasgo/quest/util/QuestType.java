package com.github.jasgo.quest.util;

public enum QuestType {
    REPEAT(1), DEFAULT(0), CHAIN(2);

    private final int id;

    QuestType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
