package com.github.jasgo.quest;

import com.github.jasgo.quest.event.CMDorChatEvent;
import com.github.jasgo.quest.event.MobKill;
import com.github.jasgo.quest.event.NPCClick;
import com.github.jasgo.quest.quests.NPCInteractQuest;
import com.github.jasgo.quest.util.QuestLoader;
import com.github.jasgo.quest.util.QuestManager;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new CMDorChatEvent(), this);
        this.getServer().getPluginManager().registerEvents(new MobKill(), this);
        this.getServer().getPluginManager().registerEvents(new NPCClick(), this);
        File dir = new File(getDataFolder() + "/quest/");
        if (!dir.exists())
            dir.mkdirs();
        QuestLoader.initQuests();
        if (QuestLoader.questFile.size() > 0) {
            QuestLoader.questFile.forEach(file -> Bukkit.getLogger().info("Quest Enabled : " + file.getName()));
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("quest") || label.equalsIgnoreCase("q") || label.equalsIgnoreCase("퀘스트") || label.equalsIgnoreCase("퀘")) {
            if (sender.isOp()) {
                if (args.length >= 1) {
                    if (args[0].equalsIgnoreCase("setnpc") || args[0].equalsIgnoreCase("엔피시설정")) {
                        if(args.length == 2) {
                            if (CitizensAPI.getDefaultNPCSelector().getSelected(sender) != null) {
                                QuestLoader.questFile.forEach(file -> {
                                    if(file.getName().contains(args[2])) {
                                        file.set("npc", CitizensAPI.getDefaultNPCSelector().getSelected(sender).getUniqueId().toString());
                                    }
                                });
                            } else {
                                sender.sendMessage("NPC를 지정해주세요!");
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("리로드")) {
                        QuestLoader.initQuests();
                        if (QuestLoader.questFile.size() > 0) {
                            QuestLoader.questFile.forEach(file -> Bukkit.getLogger().info("Quest Enabled : " + file.getName()));
                        }
                    } else if(args[0].equalsIgnoreCase("settarget") || args[0].equalsIgnoreCase("타깃설정")) {
                        if(args.length == 2) {
                            if (CitizensAPI.getDefaultNPCSelector().getSelected(sender) != null) {
                                QuestLoader.questFile.forEach(file -> {
                                    if(file.getName().contains(args[2])) {
                                        if(QuestLoader.toQuest(file) instanceof NPCInteractQuest) {
                                            file.set("target", CitizensAPI.getDefaultNPCSelector().getSelected(sender).getUniqueId().toString());
                                        }
                                    }
                                });
                            } else {
                                sender.sendMessage("NPC를 지정해주세요!");
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
