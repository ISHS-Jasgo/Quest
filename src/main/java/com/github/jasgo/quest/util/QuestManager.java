package com.github.jasgo.quest.util;

import com.github.jasgo.levellib.util.LevelUtil;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class QuestManager {
    private static HashMap<Player, Quest> quests = new HashMap<>();
    private static HashMap<Player, List<Quest>> clear = new HashMap<>();
    public static List<NPC> questNPC = new ArrayList<>();
    public static HashMap<NPC, Quest> npcQuest = new HashMap<>();

    public static Quest getQuest(Player player) {
        if (quests.containsKey(player)) {
            return quests.get(player);
        } else {
            return null;
        }
    }

    public static List<Quest> getClearQuests(Player player) {
        if (clear.containsKey(player)) {
            return clear.get(player);
        } else {
            return null;
        }
    }

    public static boolean giveQuest(Player player, Quest quest) {
        if (!quests.containsKey(player)) {
            if (clear.containsKey(player)) {
                if (!clear.get(player).contains(quest)) {
                    quests.put(player, quest);
                    return true;
                } else {
                    if (quest.getType() == QuestType.REPEAT) {
                        quests.put(player, quest);
                        return true;
                    } else {
                        return false;
                    }
                }
            } else {
                quests.put(player, quest);
                return true;
            }
        } else {
            return false;
        }
    }

    public static boolean clearQuest(Player player) {
        if (quests.containsKey(player)) {
            if (clear.containsKey(player)) {
                clear.get(player).add(quests.get(player));
            } else {
                clear.put(player, new ArrayList<>(Arrays.asList(quests.get(player))));
            }
            getQuest(player).getReward().forEach(reward -> player.getInventory().addItem(reward));
            LevelUtil.setExp(player, LevelUtil.getExp(player) + getQuest(player).getExp());
            if(LevelUtil.getExp(player) > LevelUtil.getMaxExp(player)) {
                LevelUtil.setExp(player, 0);
                LevelUtil.setLevel(player, LevelUtil.getLevel(player) + 1);
                //수정해야함!!
            }
            quests.remove(player);
            return true;
        } else {
            return false;
        }
    }
}
