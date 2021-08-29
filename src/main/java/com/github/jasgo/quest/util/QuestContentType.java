package com.github.jasgo.quest.util;

public enum QuestContentType {
    KillMobs(0), CMDorChat(1), NPCInteract(2), GatherItems(3);

    private final int id;
    QuestContentType(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
}
