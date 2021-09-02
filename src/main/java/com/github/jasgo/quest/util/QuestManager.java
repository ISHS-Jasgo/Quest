package com.github.jasgo.quest.util;

import com.github.jasgo.levellib.util.LevelUtil;
import com.github.jasgo.quest.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class QuestManager {
    public static HashMap<Player, Quest> quests = new HashMap<>();
    private static HashMap<Player, List<Quest>> clear = new HashMap<>();
    public static HashMap<UUID, Quest> npcQuest = new HashMap<>();

    @Nullable
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
            return new ArrayList<>();
        }
    }

    public static boolean giveQuest(Player player, Quest quest) {
        if (!quests.containsKey(player)) {
            if (clear.containsKey(player)) {
                if (!clear.get(player).contains(quest)) {
                    quests.put(player, quest);
                    saveQuests(player);
                    return true;
                } else {
                    if (quest.getType() == QuestType.REPEAT) {
                        quests.put(player, quest);
                        saveQuests(player);
                        return true;
                    } else {
                        return false;
                    }
                }
            } else {
                quests.put(player, quest);
                saveQuests(player);
                return true;
            }
        } else {
            return false;
        }
    }

    public static boolean clearQuest(Player player) {
        if (quests.containsKey(player)) {
            Quest child = getQuest(player).getChild() != null ? getQuest(player).getChild() : null;
            if (clear.containsKey(player)) {
                clear.get(player).add(quests.get(player));
            } else {
                clear.put(player, new ArrayList<>(Arrays.asList(quests.get(player))));
            }
            getQuest(player).getReward().forEach(reward -> player.getInventory().addItem(reward));
            quests.remove(player);
            if(child != null) {
                giveQuest(player, child);
                player.sendMessage(child.getName() + "퀘스트를 받았습니다!");
            }
            LevelUtil.setExp(player, LevelUtil.getExp(player) + getQuest(player).getExp());
            if(LevelUtil.getExp(player) > LevelUtil.getMaxExp(player)) {
                LevelUtil.setExp(player, 0);
                LevelUtil.setLevel(player, LevelUtil.getLevel(player) + 1);
                //수정해야함!!
            }
            saveQuests(player);
            return true;
        } else {
            return false;
        }
    }
    public static void removeQuest(Player player) {
        if(quests.containsKey(player)) {
            quests.remove(player);
        }
    }
    public static Quest getQuestByName(String name) {
        AtomicReference<Quest> atomicQuest = new AtomicReference<>();
        npcQuest.forEach((uuid, quest) -> {
            if(quest.getName().equalsIgnoreCase(name)) {
                atomicQuest.set(quest);
            }
        });
        return atomicQuest.get();
    }
    public static void saveQuests(Player player) {
        File f = new File(Main.getPlugin(Main.class).getDataFolder() + "/user/" + player.getUniqueId().toString() + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(f);
        List<String> cq = new ArrayList<>();
        getClearQuests(player).forEach(quest -> cq.add(quest.getName()));
        config.set("clear", cq);
        if(getQuest(player) != null) {
            config.set("quest", getQuest(player).getName());
        }
        try {
            config.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void initQuests(Player player) {
        File f = new File(Main.getPlugin(Main.class).getDataFolder() + "/user/" + player.getUniqueId().toString() + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(f);
        if(f.exists()) {
            clear.put(player, new ArrayList<>());
            config.getStringList("clear").forEach(c -> clear.get(player).add(getQuestByName(c)));
            quests.put(player, getQuestByName(config.getString("quest")));
        }
    }
}
