package com.github.jasgo.quest.util;

import com.github.jasgo.quest.Main;
import net.citizensnpcs.api.CitizensAPI;
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
import java.util.concurrent.atomic.AtomicReference;

public class QuestLoader {
    public static List<FileConfiguration> questFile = new ArrayList<>();
    public static List<Quest> questList = new ArrayList<>();

    public static void initQuests() {
        File dir = new File(Main.getPlugin(Main.class).getDataFolder() + "/quest/");
        for(File f : dir.listFiles()) {
            if(f.getName().contains("quest_")) {
                FileConfiguration config = YamlConfiguration.loadConfiguration(f);
                if(isQuest(config)) {
                    questFile.add(config);
                }
            }
        }
        questFile.forEach(qf -> {
            String name = qf.getName().replace("quest_", "");
            int qtid = qf.getInt("type");
            int qctid = qf.getInt("content-type");
            UUID uuid = UUID.fromString("npc");
        });
    }
    public static boolean isQuest(FileConfiguration config) {
        AtomicBoolean result = new AtomicBoolean(false);
        CitizensAPI.getNPCRegistries().forEach(npcs -> {
            if(npcs.getByUniqueId(UUID.fromString(config.getString("npc"))) != null) {
                int type = config.getInt("type");
                if(type >= 0 && type <= 2) {
                    result.set(true);
                }
            }
        });
        return result.get();
    }
    public static List<ItemStack> getRewardsofQuestFile(FileConfiguration config) {
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
