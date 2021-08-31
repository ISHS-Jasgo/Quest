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
    private final String name;

    public Quest(String name, NPC npc, QuestType type, QuestContentType content, List<ItemStack> reward, int exp) {
        this.npc = npc;
        this.type = type;
        this.content = content;
        this.reward = reward;
        this.exp = exp;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public int getExp() {
        return exp;
    }
}
