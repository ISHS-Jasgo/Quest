package com.github.jasgo.quest.util;

import net.citizensnpcs.api.npc.NPC;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class Quest {
    private UUID npc;
    private final QuestType type;
    private final QuestContentType content;
    private final List<ItemStack> reward;
    private final int exp;
    private final String name;
    private Quest child;

    public Quest(String name, UUID npc, QuestType type, QuestContentType content, List<ItemStack> reward, int exp, @Nullable Quest child) {
        this.npc = npc;
        this.type = type;
        this.content = content;
        this.reward = reward;
        this.exp = exp;
        this.name = name;
        if (child == null)
            this.child = null;
        else
            this.child = child;
    }

    public UUID getNpc() {
        return npc;
    }

    public void setNpc(UUID npc) {
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

    public String getName() {
        return name;
    }

    public int getExp() {
        return exp;
    }

    public Quest getChild() {
        return child;
    }
    public void setChild(Quest child) {
        this.child = child;
    }
}
