package com.github.jasgo.quest.util;

import com.github.jasgo.quest.Main;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class QuestLoader {
    public static List<FileConfiguration> questFile = new ArrayList<>();

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
}
