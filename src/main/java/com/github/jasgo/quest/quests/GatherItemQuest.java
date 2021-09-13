package com.github.jasgo.quest.quests;

import com.github.jasgo.quest.util.Quest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class GatherItemQuest extends Quest {

    private final List<ItemStack> needs;
    public GatherItemQuest(Quest quest, List<ItemStack> needs) {
        super(quest.getName(), quest.getNpc(), quest.getType(), quest.getContent(), quest.getReward(), quest.getExp(), quest.getStart(), quest.getEnd(), quest.getChild());
        this.needs = needs;
    }

    public List<ItemStack> getNeeds() {
        return needs;
    }
    public boolean isClear(Player player) {
        AtomicBoolean bool = new AtomicBoolean(true);
        needs.forEach(item -> {
            if(!player.getInventory().contains(item)) {
                bool.set(false);
            }
        });
        if(bool.get()) {
            needs.forEach(item -> player.getInventory().removeItem(item));
        }
        return bool.get();
    }
}
