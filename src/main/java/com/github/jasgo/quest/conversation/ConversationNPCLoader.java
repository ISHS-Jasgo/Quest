package com.github.jasgo.quest.conversation;

import com.github.jasgo.quest.Main;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ConversationNPCLoader {

    public static HashMap<UUID, ConversationNPC> conversationNPCList = new HashMap<>();


    public static void initConversationNPCS() {
        File file = new File(Main.getPlugin(Main.class).getDataFolder() + "/cnpc/");
        for (File listFile : file.listFiles()) {
            if (listFile.getName().contains("cnpc_")) {
                FileConfiguration config = YamlConfiguration.loadConfiguration(listFile);
                if (isConversationNPC(config)) {
                    conversationNPCList.put(getConversationNPC(config).getNpc(), getConversationNPC(config));
                }
            }
        }
    }

    public static ConversationNPC getConversationNPC(FileConfiguration config) {
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(ConversationLoader.getConversationFile(config.getString("conversation")));
        Conversation conversation = ConversationLoader.getConversation(configuration);
        UUID uuid = UUID.fromString(config.getString("npc"));
        return new ConversationNPC(conversation, uuid);
    }

    public static boolean isConversationNPC(FileConfiguration config) {
        if (config.contains("conversation")) {
            File f = ConversationLoader.getConversationFile(config.getString("conversation"));
            if (ConversationLoader.isConversation(YamlConfiguration.loadConfiguration(f))) {
                return true;
            }
        }
        return false;
    }
    public static File getConversationNPCFile(String name) {
        return new File(Main.getPlugin(Main.class).getDataFolder() + "/cnpc/" + name);
    }
}
