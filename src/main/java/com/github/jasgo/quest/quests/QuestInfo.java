package com.github.jasgo.quest.quests;

import com.github.jasgo.quest.util.Quest;
import io.lumine.xikage.mythicmobs.api.bukkit.BukkitAPIHelper;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class QuestInfo {
    private final Quest quest;
    private final Player player;

    public QuestInfo(Quest quest, Player player) {
        this.quest = quest;
        this.player = player;
    }

    public Quest getQuest() {
        return quest;
    }
    public Inventory getQuestGui() {
        String name = quest.getName();
        List<String> explain = quest.getStart().getLines();
        String goal = "";
        if(quest instanceof KillMobsQuest) {
            KillMobsQuest killMobsQuest = (KillMobsQuest) quest;
            BukkitAPIHelper helper = new BukkitAPIHelper();
            String mob = helper.getMythicMob(killMobsQuest.getTarget().name()).getDisplayName().get();
            int progress = killMobsQuest.getProgress(player);
            int g = killMobsQuest.getGoal();
            goal = mob + "을(를) " + g + "마리 처치하세요 ( " + progress + " / " + g + " )";
        } else if(quest instanceof CMDorChatQuest) {
            CMDorChatQuest cmdOrChatQuest = (CMDorChatQuest) quest;
            String text = cmdOrChatQuest.getText();
            goal = cmdOrChatQuest.isCmd() ? "명령어 \"/" + text + "\"를 입력하세요" : "채팅창에 \"" + text + "\"를 입력하세요";
        } else if(quest instanceof NPCInteractQuest) {
            NPCInteractQuest npcInteractQuest = (NPCInteractQuest) quest;
            String target = CitizensAPI.getNPCRegistry().getByUniqueId(npcInteractQuest.getTarget()).getName();
            goal = target + "와 대화하세요";
        } else if(quest instanceof GatherItemQuest) {
            GatherItemQuest gatherItemQuest = (GatherItemQuest) quest;
            List<String> items = new ArrayList<>();
            gatherItemQuest.getNeeds().forEach(need -> items.add(need.getItemMeta().getDisplayName() + " " + need.getAmount() + "개"));
            String list = items.toString().replace("[", "").replace("]", "");
            goal = list + "를 모으세요";
        }
        List<String> rewards = new ArrayList<>();
        quest.getReward().forEach(reward -> rewards.add(reward.getItemMeta().getDisplayName()));
        String reward = rewards.toString().replace("[", "").replace("]", "");
        Inventory inv = Bukkit.createInventory(null, 9, "퀘스트 정보");
        ItemStack item = new ItemStack(Material.BOOK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        List<String> lore = new ArrayList<>();
        explain.forEach(line -> lore.add(ChatColor.ITALIC + (ChatColor.GRAY + "\"" + line.trim() + "\"")));
        lore.add("목표: " + goal);
        lore.add("보상: " + reward);
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(0, item);
        return inv;
    }

    public Player getPlayer() {
        return player;
    }
}
