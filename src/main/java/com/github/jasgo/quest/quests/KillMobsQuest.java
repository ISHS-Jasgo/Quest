package com.github.jasgo.quest.quests;

import com.github.jasgo.levellib.mobs.Mob;
import com.github.jasgo.quest.util.Quest;
import com.github.jasgo.quest.util.QuestContentType;
import com.github.jasgo.quest.util.QuestType;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class KillMobsQuest extends Quest {
    private final Mob target;
    private final int goal;
    private int count;

    public KillMobsQuest(Quest quest, Mob target, int goal) {
        super(quest.getName(), quest.getNpc(), quest.getType(), quest.getContent(), quest.getReward(), quest.getExp(), quest.getChild());
        this.target = target;
        this.goal = goal;
        this.count = 0;
    }

    public Mob getTarget() {
        return target;
    }

    public int getGoal() {
        return goal;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
