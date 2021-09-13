package com.github.jasgo.quest.quests;

import com.github.jasgo.quest.util.Quest;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NPCInteractQuest extends Quest {

    private final UUID target;
    private List<Player> clear = new ArrayList<>();

    public NPCInteractQuest(Quest quest, UUID target) {
        super(quest.getName(), quest.getNpc(), quest.getType(), quest.getContent(), quest.getReward(), quest.getExp(), quest.getStart(), quest.getEnd(), quest.getChild());
        this.target = target;
    }

    public UUID getTarget() {
        return target;
    }
    public void setClear(Player player) {
        clear.add(player);
    }
    public boolean isClear(Player player) {
        return clear.contains(player);
    }
    public void removeClear(Player player) {
        if(clear.contains(player)) {
            clear.remove(player);
        }
    }
}
