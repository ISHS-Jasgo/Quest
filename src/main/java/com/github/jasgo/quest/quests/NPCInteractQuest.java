package com.github.jasgo.quest.quests;

import com.github.jasgo.quest.util.Quest;
import net.citizensnpcs.api.npc.NPC;

import java.util.UUID;

public class NPCInteractQuest extends Quest {

    private final UUID target;

    public NPCInteractQuest(Quest quest, UUID target) {
        super(quest.getName(), quest.getNpc(), quest.getType(), quest.getContent(), quest.getReward(), quest.getExp(), quest.getStart(), quest.getEnd(), quest.getChild());
        this.target = target;
    }

    public UUID getTarget() {
        return target;
    }
}
