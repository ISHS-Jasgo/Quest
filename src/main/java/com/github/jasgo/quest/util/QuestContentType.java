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
    public static QuestContentType getById(int id) {
        if(id == 0) {
            return KillMobs;
        } else if (id == 1) {
            return CMDorChat;
        } else if (id == 2) {
            return NPCInteract;
        } else if (id == 3) {
            return GatherItems;
        } else {
            return null;
        }
    }
}
