package com.github.jasgo.quest.event;

import com.github.jasgo.quest.util.QuestManager;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobDeathEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class NPCClick implements Listener {
    @EventHandler
    public void onClick(NPCRightClickEvent event) {
        if(QuestManager.questNPC.contains(event.getNPC())) {
            boolean getQuest = QuestManager.giveQuest(event.getClicker(), QuestManager.npcQuest.get(event.getNPC()));
            if(getQuest) {
                event.getClicker().sendMessage(QuestManager.getQuest(event.getClicker()).getName() + "퀘스트를 받았습니다!");
            } else {
                event.getClicker().sendMessage("당신은 이미 다른 퀘스트를 진행중이거나 이 퀘스트를 클리어했습니다!");
            }
        }
    }
}
