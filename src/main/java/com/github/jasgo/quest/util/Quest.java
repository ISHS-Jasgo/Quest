package com.github.jasgo.quest.util;

import com.github.jasgo.levellib.mobs.Mob;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Quest {
    private NPC npc;
    private final QuestType type;
    private final QuestContentType content;
    private final List<ItemStack> reward;
    private final int exp;
    private Mob mob;

    public Quest(NPC npc, QuestType type, QuestContentType content, List<ItemStack> reward, int exp) {
        this.npc = npc;
        this.type = type;
        this.content = content;
        this.reward = reward;
        this.exp = exp;
    }
    public Quest(NPC npc, QuestType type, QuestContentType content, List<ItemStack> reward, int exp, Mob mob) {
        this.npc = npc;
        this.type = type;
        this.content = content;
        this.reward = reward;
        this.exp = exp;
        this.mob = mob;
    }

    public NPC getNpc() {
        return npc;
    }

    public void setNpc(NPC npc) {
        this.npc = npc;
    }

    public QuestType getType() {
        return type;
    }

    public QuestContentType getContent() {
        return content;
    }

    public List<ItemStack> getReward() {
        return reward;
    }
}
