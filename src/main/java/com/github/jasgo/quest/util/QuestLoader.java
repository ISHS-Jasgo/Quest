package com.github.jasgo.quest.util;

import com.github.jasgo.levellib.mobs.Mob;
import com.github.jasgo.quest.Main;
import com.github.jasgo.quest.conversation.Conversation;
import com.github.jasgo.quest.quests.CMDorChatQuest;
import com.github.jasgo.quest.quests.KillMobsQuest;
import com.github.jasgo.quest.quests.NPCInteractQuest;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class QuestLoader {
    public static List<FileConfiguration> questFile = new ArrayList<>();

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
            toQuest(qf);
        });
    }

    public static boolean isQuest(FileConfiguration config) {
        AtomicBoolean result = new AtomicBoolean(false);
        int type = config.getInt("type");
        if (type >= 0 && type <= 2) {
            int content = config.getInt("content-type");
            if (content >= 0 && content <= 3) {
                result.set(true);
            }
        }
        return result.get();
    }

    public static List<ItemStack> getRewardListofQuestFile(FileConfiguration config) {
        List<Map<?, ?>> rewardList = config.getMapList("reward");
        List<ItemStack> rewards = new ArrayList<>();
        rewardList.forEach(reward -> {
            String name = ChatColor.translateAlternateColorCodes('&', (String) reward.get("name"));
            String m = ((String) reward.get("material")).replace(" ", "_").toUpperCase();
            String material = Material.valueOf(m) == null ? "air" : m;
            List<String> array = (List<String>) reward.get("lore");
            List<String> lore = new ArrayList<>();
            array.forEach(a -> lore.add(ChatColor.translateAlternateColorCodes('&', a)));
            int dmg = (int) reward.get("data");
            short damage = (short) dmg;
            int amount = (int) reward.get("amount");
            ItemStack item = new ItemStack(Material.valueOf(material), amount, damage);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(name);
            meta.setLore(lore);
            item.setItemMeta(meta);
            rewards.add(item);
        });
        return rewards;
    }
    public static ItemStack getStartConversationItem(FileConfiguration config) {
        if(config.contains("conversation.start.offhand")) {
            String path = "conversation.start.offhand.";
            String m = config.getString(path + "material").replace(" ", "_").toUpperCase();
            String material = Material.valueOf(m) == null ? "air" : m;
            int dmg = config.getInt(path + "data");
            short damage = (short) dmg;
            return new ItemStack(Material.valueOf(material), 1, damage);
        } else {
            return new ItemStack(Material.AIR);
        }
    }
    public static ItemStack getEndConversationItem(FileConfiguration config) {
        if(config.contains("conversation.end.offhand")) {
            String path = "conversation.end.offhand.";
            String m = config.getString(path + "material").replace(" ", "_").toUpperCase();
            String material = Material.valueOf(m) == null ? "air" : m;
            int dmg = config.getInt(path + "data");
            short damage = (short) dmg;
            return new ItemStack(Material.valueOf(material), 1, damage);
        } else {
            return new ItemStack(Material.AIR);
        }
    }
    public static Conversation getStartConversation(FileConfiguration config) {
        if(config.contains("conversation.start.lines") && config.contains("conversation.start.delay")) {
            List<String> l = config.getStringList("conversation.start.lines");
            List<String> lines = new ArrayList<>();
            l.forEach(line -> lines.add(ChatColor.translateAlternateColorCodes('&', line)));
            long delay = config.getLong("conversation.start.delay");
            ItemStack offhand = getStartConversationItem(config);
            Conversation conversation = new Conversation(lines, delay, offhand);
            return conversation;
        }
        return new Conversation(Arrays.asList(""), 0L, new ItemStack(Material.AIR));
    }
    public static Conversation getEndConversation(FileConfiguration config) {
        if(config.contains("conversation.end.lines") && config.contains("conversation.end.delay")) {
            List<String> l = config.getStringList("conversation.end.lines");
            List<String> lines = new ArrayList<>();
            l.forEach(line -> lines.add(ChatColor.translateAlternateColorCodes('&', line)));
            long delay = config.getLong("conversation.end.delay");
            ItemStack offhand = getStartConversationItem(config);
            Conversation conversation = new Conversation(lines, delay, offhand);
            return conversation;
        }
        return new Conversation(Arrays.asList(""), 0L, new ItemStack(Material.AIR));
    }

    public static Quest toQuest(FileConfiguration config) {
        String name = config.getString("name");
        int type = config.getInt("type");
        int content = config.getInt("content-type");
        UUID uuid = UUID.fromString(config.getString("npc"));
        List<ItemStack> rewardList = getRewardListofQuestFile(config);
        int exp = config.getInt("exp");
        Conversation startConversation = getStartConversation(config);
        Conversation endConversation = getEndConversation(config);
        Quest quest = new Quest(name, uuid, QuestType.getById(type), QuestContentType.getById(content), rewardList, exp, startConversation, endConversation, null);
        if (content == 0) {
            if (config.contains("mob") && config.contains("goal")) {
                quest = new KillMobsQuest(quest, Mob.valueOf(config.getString("mob")), config.getInt("goal"));
            }
        } else if (content == 1) {
            if (config.contains("text") && config.contains("isCMD")) {
                quest = new CMDorChatQuest(quest, config.getString("text"), config.getBoolean("isCMD"));
            }
        } else if (content == 2) {
            if (config.contains("target")) {
                quest = new NPCInteractQuest(quest, UUID.fromString(config.getString("target")));
            }
        }
        if (config.contains("child")) {
            File f = new File(Main.getPlugin(Main.class).getDataFolder() + "/quest/" + config.getString("child"));
            if (f.exists()) {
                quest.setChild(toQuest(YamlConfiguration.loadConfiguration(f)));
            }
        }
        QuestManager.npcQuest.put(uuid, quest);
        return quest;
    }
}
