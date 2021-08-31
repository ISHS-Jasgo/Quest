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
    public static QuestType getById(int id) {
        if(id == 0) {
            return DEFAULT;
        } else if (id == 1) {
            return REPEAT;
        } else if (id == 2) {
            return CHAIN;
        } else {
            return null;
        }
    }
}
