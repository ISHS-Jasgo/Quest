package com.github.jasgo.quest.conversation;

import com.github.jasgo.quest.Main;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConversationLoader {

    public static List<Conversation> conversationList = new ArrayList<>();

    public static void initConversations() {
        File f = new File(Main.getPlugin(Main.class).getDataFolder() + "/conversation/");
        for (File file : f.listFiles()) {
            if(file.getName().contains("conversation_")) {
                FileConfiguration config = YamlConfiguration.loadConfiguration(file);
                if (isConversation(config)) {
                    conversationList.add(getConversation(config));
                }
            }
        }
    }

    public static Conversation getConversation(FileConfiguration config) {
        List<String> lines = config.getStringList("lines");
        long delay = config.getLong("delay");
        ItemStack item = getConversationItem(config);
        return new Conversation(lines, delay, item);
    }

    public static ItemStack getConversationItem(FileConfiguration config) {
        String material = config.getString("material").replace(" ", "_").toUpperCase();
        int data = config.getInt("data");
        ItemStack item = new ItemStack(Material.valueOf(material));
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(data);
        item.setItemMeta(meta);
        return item;
    }

    public static File getConversationFile(String name) {
        return new File(Main.getPlugin(Main.class).getDataFolder() + "/conversation/" + name);
    }

    public static boolean isConversation(FileConfiguration config) {
        if (config.contains("lines") && config.contains("delay") && config.contains("material") && config.contains("data")) {
            for (Material value : Material.values()) {
                if(value.name().equalsIgnoreCase(config.getString("material").replace(" ", "_").toUpperCase())) {
                    return true;
                }
            }
        }
        return false;
    }
}
