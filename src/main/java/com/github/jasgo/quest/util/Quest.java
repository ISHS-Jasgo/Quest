package com.github.jasgo.quest.util;

import net.citizensnpcs.api.npc.NPC;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Quest {
    private NPC npc;
    private final QuestType type;
    private final QuestContent content;
    private final List<ItemStack> reward;
    private final int exp;

    public Quest(NPC npc, QuestType type, QuestContent content, List<ItemStack> reward, int exp) {
        this.npc = npc;
        this.type = type;
        this.content = content;
        this.reward = reward;
        this.exp = exp;
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

    public QuestContent getContent() {
        return content;
    }

    public List<ItemStack> getReward() {
        return reward;
    }
}
