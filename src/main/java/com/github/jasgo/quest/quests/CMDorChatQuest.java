package com.github.jasgo.quest.quests;

import com.github.jasgo.quest.util.Quest;

public class CMDorChatQuest extends Quest {

    private final String text;
    private final boolean isCmd;

    public CMDorChatQuest(Quest quest, String text, boolean isCmd) {
        super(quest.getName(), quest.getNpc(), quest.getType(), quest.getContent(), quest.getReward(), quest.getExp(), quest.getStart(), quest.getEnd(), quest.getChild());
        this.text = text;
        this.isCmd = isCmd;
    }

    public String getText() {
        return text;
    }

    public boolean isCmd() {
        return isCmd;
    }
}
