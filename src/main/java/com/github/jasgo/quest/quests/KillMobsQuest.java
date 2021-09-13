package com.github.jasgo.quest.quests;

import com.github.jasgo.levellib.mobs.Mob;
import com.github.jasgo.quest.util.Quest;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class KillMobsQuest extends Quest {
    private final Mob target;
    private final int goal;
    public HashMap<Player, Integer> progress = new HashMap<>();

    public KillMobsQuest(Quest quest, Mob target, int goal) {
        super(quest.getName(), quest.getNpc(), quest.getType(), quest.getContent(), quest.getReward(), quest.getExp(), quest.getStart(), quest.getEnd(), quest.getChild());
        this.target = target;
        this.goal = goal;
    }

    public Mob getTarget() {
        return target;
    }

    public int getGoal() {
        return goal;
    }

    public void setProgress(Player player, int value) {
        progress.put(player, value);
    }
    public int getProgress(Player player) {
        return progress.containsKey(player) ? progress.get(player) : 0;
    }
    public void addProgress(Player player, int value) {
        if(progress.containsKey(player)) {
            setProgress(player, getProgress(player) + value);
        } else {
            setProgress(player, value);
        }
    }
    public boolean isClear(Player player) {
        return getProgress(player) >= getGoal();
    }

}
