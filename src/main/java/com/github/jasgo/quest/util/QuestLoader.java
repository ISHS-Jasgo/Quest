package com.github.jasgo.quest.util;

import com.github.jasgo.levellib.mobs.Mob;
import com.github.jasgo.quest.Main;
import com.github.jasgo.quest.quests.CMDorChatQuest;
import com.github.jasgo.quest.quests.KillMobsQuest;
import com.github.jasgo.quest.quests.NPCInteractQuest;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class QuestLoader {
    public static List<FileConfiguration> questFile = new ArrayList<>();
    public static List<Quest> questList = new ArrayList<>();

    public static void initQuests() {
        File dir = new File(Main.getPlugin(Main.class).getDataFolder() + "/quest/");
        for (File f : dir.listFiles()) {
            if (f.getName().contains("quest_")) {
                FileConfiguration config = YamlConfiguration.loadConfiguration(f);
                if (isQuest(config)) {
                    questFile.add(config);
                }
            }
        }
        questFileToQuest();
    }

    public static void questFileToQuest() {
        questFile.forEach(qf -> {
            String name = qf.getName().replace("quest_", "");
            int qtid = qf.getInt("type");
            int qctid = qf.getInt("content-type");
            UUID uuid = UUID.fromString("npc");
            NPC npc = CitizensAPI.getNPCRegistry().getByUniqueId(uuid);
            List<ItemStack> rewardList = getRewardListofQuestFile(qf);
            int exp = qf.getInt("exp");
            Quest quest = new Quest(name, npc, QuestType.getById(qtid), QuestContentType.getById(qctid), rewardList, exp);
            if (qctid == 0) {
                if (qf.contains("mob") && qf.contains("goal")) {
                    KillMobsQuest killMobsQuest = new KillMobsQuest(quest, Mob.valueOf(qf.getString("mob")), qf.getInt("goal"));
                    QuestManager.npcQuest.put(npc, killMobsQuest);
                }
            } else if (qctid == 1) {
                if (qf.contains("text") && qf.contains("isCMD")) {
                    CMDorChatQuest cmDorChatQuest = new CMDorChatQuest(quest, qf.getString("text"), qf.getBoolean("isCMD"));
                    QuestManager.npcQuest.put(npc, cmDorChatQuest);
                }
            } else if (qctid == 2) {
                if (qf.contains("target")) {
                    NPCInteractQuest npcInteractQuest = new NPCInteractQuest(quest, CitizensAPI.getNPCRegistry().getByUniqueId(UUID.fromString(qf.getString("target"))));
                    QuestManager.npcQuest.put(npc, npcInteractQuest);
                }
            }
        });
    }

    public static boolean isQuest(FileConfiguration config) {
        AtomicBoolean result = new AtomicBoolean(false);
        CitizensAPI.getNPCRegistries().forEach(npcs -> {
            if (npcs.getByUniqueId(UUID.fromString(config.getString("npc"))) != null) {
                int type = config.getInt("type");
                if (type >= 0 && type <= 2) {
                    int content = config.getInt("content-type");
                    if (content >= 0 && content <= 3) {
                        result.set(true);
                    }

                }
            }
        });
        return result.get();
    }

    public static List<ItemStack> getRewardListofQuestFile(FileConfiguration config) {
        List<Map<?, ?>> rewardList = config.getMapList("reward");
        List<ItemStack> rewards = new ArrayList<>();
        rewardList.forEach(reward -> {
            String name = (String) reward.get("name");
            String material = ((String) reward.get("material")).replace(" ", "_").toUpperCase();
            List<String> lore = (List<String>) reward.get("lore");
            byte data = (byte) reward.get("data");
            short damage = (short) reward.get("damage");
            int amount = (int) reward.get("amount");
            ItemStack item = new ItemStack(Material.valueOf(material), amount, damage, data);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(name);
            meta.setLore(lore);
            item.setItemMeta(meta);
            rewards.add(item);
        });
        return rewards;
    }
}
