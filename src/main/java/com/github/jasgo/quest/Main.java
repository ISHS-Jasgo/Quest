package com.github.jasgo.quest;

import com.github.jasgo.quest.util.QuestLoader;
import com.github.jasgo.quest.util.QuestManager;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        File dir = new File(getDataFolder() + "/quest/");
        if(!dir.exists())
            dir.mkdirs();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("quest") || label.equalsIgnoreCase("q") || label.equalsIgnoreCase("퀘스트") || label.equalsIgnoreCase("퀘")) {
            if(sender.isOp()) {
                if(args.length >= 1) {
                    if(args[0].equalsIgnoreCase("npcadd") || args[0].equalsIgnoreCase("엔피시추가")) {
                        if (CitizensAPI.getDefaultNPCSelector().getSelected(sender) != null) {
                            QuestManager.questNPC.add(CitizensAPI.getDefaultNPCSelector().getSelected(sender));
                        } else {
                            sender.sendMessage("NPC를 지정해주세요!");
                        }
                    } else if(args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("리로드")) {
                        QuestLoader.initQuests();
                    }
                }
            }
        }
        return false;
    }
}
