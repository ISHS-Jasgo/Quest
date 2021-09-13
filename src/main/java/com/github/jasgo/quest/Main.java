package com.github.jasgo.quest;

import com.github.jasgo.jasgodb.db.Database;
import com.github.jasgo.quest.conversation.ConversationLoader;
import com.github.jasgo.quest.conversation.ConversationNPCLoader;
import com.github.jasgo.quest.event.*;
import com.github.jasgo.quest.quests.*;
import com.github.jasgo.quest.util.Quest;
import com.github.jasgo.quest.util.QuestLoader;
import com.github.jasgo.quest.util.QuestManager;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.lumine.xikage.mythicmobs.MythicMobs;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new OffHandEvent(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        this.getServer().getPluginManager().registerEvents(new CMDorChatEvent(), this);
        this.getServer().getPluginManager().registerEvents(new MobKill(), this);
        this.getServer().getPluginManager().registerEvents(new NPCClick(), this);
        this.getServer().getPluginManager().registerEvents(new EntityDeath(), this);
        File dir = new File(getDataFolder() + "/quest/");
        File dir2 = new File(getDataFolder() + "/user/");
        File dir3 = new File(getDataFolder() + "/conversation/");
        File dir4 = new File(getDataFolder() + "/cnpc/");
        if (!dir.exists())
            dir.mkdirs();
        if (!dir2.exists())
            dir2.mkdirs();
        if (!dir3.exists())
            dir3.mkdirs();
        if (!dir4.exists())
            dir4.mkdirs();
        QuestLoader.initQuests();
        ConversationNPCLoader.initConversationNPCS();
        ConversationLoader.initConversations();
        if (QuestLoader.questFile.size() > 0) {
            QuestLoader.questFile.forEach(file -> Bukkit.getLogger().info("Quest Enabled : " + file.getString("name")));
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("quest") || label.equalsIgnoreCase("q") || label.equalsIgnoreCase("퀘스트") || label.equalsIgnoreCase("퀘")) {
            if (args.length >= 1) {
                if (sender.isOp()) {
                    if (args[0].equalsIgnoreCase("setnpc") || args[0].equalsIgnoreCase("엔피시설정")) {
                        if (args.length == 2) {
                            if (CitizensAPI.getDefaultNPCSelector().getSelected(sender) != null) {
                                File f = new File(getDataFolder() + "/quest/" + args[1] + ".yml");
                                if (f.exists()) {
                                    FileConfiguration file = YamlConfiguration.loadConfiguration(f);
                                    file.set("npc", CitizensAPI.getDefaultNPCSelector().getSelected(sender).getUniqueId().toString());
                                    try {
                                        file.save(f);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } else {
                                sender.sendMessage("NPC를 지정해주세요!");
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("리로드")) {
                        QuestLoader.questFile.clear();
                        QuestManager.npcQuest.clear();
                        QuestLoader.initQuests();
                        if (QuestLoader.questFile.size() > 0) {
                            QuestLoader.questFile.forEach(file -> {
                                Bukkit.getLogger().info("Quest Enabled : " + file.getString("name"));
                                sender.sendMessage("Quest Enabled : " + file.getString("name"));
                                Bukkit.getOnlinePlayers().forEach(player -> {
                                    if (QuestManager.getQuest(player) != null) {
                                        if (QuestManager.getQuest(player).getName().equalsIgnoreCase(file.getString("name"))) {
                                            QuestManager.removeQuest(player);
                                            QuestManager.giveQuest(player, QuestManager.npcQuest.get(UUID.fromString(file.getString("npc"))));
                                        }
                                    }
                                });
                            });
                        }
                    } else if (args[0].equalsIgnoreCase("settarget") || args[0].equalsIgnoreCase("타깃설정")) {
                        if (args.length == 2) {
                            if (CitizensAPI.getDefaultNPCSelector().getSelected(sender) != null) {
                                File f = new File(getDataFolder() + "/quest/" + args[1] + ".yml");
                                if (f.exists()) {
                                    FileConfiguration file = YamlConfiguration.loadConfiguration(f);
                                    file.set("target", CitizensAPI.getDefaultNPCSelector().getSelected(sender).getUniqueId().toString());
                                    try {
                                        file.save(f);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } else {
                                sender.sendMessage("NPC를 지정해주세요!");
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("cnpc")) {
                        if(args.length == 2) {
                            File f = new File(getDataFolder() + "/cnpc/cnpc_" + args[1] + ".yml");
                            if(f.exists()) {
                                FileConfiguration config = YamlConfiguration.loadConfiguration(f);
                                config.set("npc", CitizensAPI.getDefaultNPCSelector().getSelected(sender).getUniqueId().toString());
                                try {
                                    config.save(f);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            } else {
                if (QuestManager.getQuest((Player) sender) != null) {
                    Quest quest = QuestManager.getQuest((Player) sender);
                    if (quest instanceof KillMobsQuest) {
                        KillMobsQuest killMobsQuest = (KillMobsQuest) quest;
                        QuestInfo info = new QuestInfo(killMobsQuest, (Player) sender);
                        ((Player) sender).openInventory(info.getQuestGui());
                    } else if (quest instanceof NPCInteractQuest) {
                        NPCInteractQuest npcInteractQuest = (NPCInteractQuest) quest;
                        QuestInfo info = new QuestInfo(npcInteractQuest, (Player) sender);
                        ((Player) sender).openInventory(info.getQuestGui());
                    } else if (quest instanceof GatherItemQuest) {
                        GatherItemQuest gatherItemQuest = (GatherItemQuest) quest;
                        QuestInfo info = new QuestInfo(gatherItemQuest, (Player) sender);
                        ((Player) sender).openInventory(info.getQuestGui());
                    } else if (quest instanceof CMDorChatQuest) {
                        CMDorChatQuest cmDorChatQuest = (CMDorChatQuest) quest;
                        QuestInfo info = new QuestInfo(cmDorChatQuest, (Player) sender);
                        ((Player) sender).openInventory(info.getQuestGui());
                    }
                } else {
                    sender.sendMessage("당신은 현재 아무 퀘스트도 진행하고 있지 않습니다!");
                }
            }
        } else if(label.equalsIgnoreCase("db")) {
            if(args.length >= 1) {
                if(args[0].equalsIgnoreCase("set")) {
                    if(args.length == 3) {
                        String key = args[1];
                        try {
                            JsonObject object = (JsonObject) new JsonParser().parse(args[2]);
                            Database.set(key, object);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else if(args[0].equalsIgnoreCase("get")) {
                    if(args.length == 2) {
                        String key = args[1];
                        try {
                            JsonObject object = Database.get(key);
                            sender.sendMessage(object.getAsString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                } else if(args[0].equalsIgnoreCase("delete")) {
                    if(args.length == 2) {
                        String key = args[1];
                        try {
                            Database.delete(key);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else if(args[0].equalsIgnoreCase("keys")) {
                    try {
                        JsonArray array = Database.listKeys();
                        sender.sendMessage(array.getAsString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return false;
    }
}
