package com.github.jasgo.quest.util;

public enum QuestContent {
    KillMobs(0), CMDorChat(1), NPCInteract(2), GatherItems(3);

    private final int id;
    QuestContent(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
}
