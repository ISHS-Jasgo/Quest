package com.github.jasgo.quest.quests;

import com.github.jasgo.quest.util.Quest;
import net.citizensnpcs.api.npc.NPC;

public class NPCInteractQuest extends Quest {

    private final NPC target;

    public NPCInteractQuest(Quest quest, NPC target) {
        super(quest.getName(), quest.getNpc(), quest.getType(), quest.getContent(), quest.getReward(), quest.getExp());
        this.target = target;
    }

    public NPC getTarget() {
        return target;
    }
}
