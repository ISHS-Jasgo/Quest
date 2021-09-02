package com.github.jasgo.quest.event;

import com.github.jasgo.quest.quests.NPCInteractQuest;
import com.github.jasgo.quest.util.QuestContentType;
import com.github.jasgo.quest.util.QuestManager;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class NPCClick implements Listener {
    @EventHandler
    public void onClick(NPCRightClickEvent event) {
        if(QuestManager.npcQuest.containsKey(event.getNPC().getUniqueId())) {
            boolean getQuest = QuestManager.giveQuest(event.getClicker(), QuestManager.npcQuest.get(event.getNPC().getUniqueId()));
            if(getQuest) {
                event.getClicker().sendMessage(QuestManager.getQuest(event.getClicker()).getName() + "퀘스트를 받았습니다!");
            } else {
                if(QuestManager.getClearQuests(event.getClicker()).contains(QuestManager.npcQuest.get(event.getNPC().getUniqueId()))) {
                    event.getClicker().sendMessage("당신은 이미 이 퀘스트를 클리어 했습니다!");
                } else {
                    event.getClicker().sendMessage("당신은 이미 다른 퀘스트를 진행중입니다!");
                }
            }
        } else {
            if(QuestManager.getQuest(event.getClicker()) == null)
                return;
            if (QuestManager.getQuest(event.getClicker()).getContent() == QuestContentType.NPCInteract) {
                if(QuestManager.getQuest(event.getClicker()) instanceof NPCInteractQuest) {
                    NPCInteractQuest quest = (NPCInteractQuest) QuestManager.getQuest(event.getClicker());
                    if(event.getNPC().getUniqueId().equals(quest.getTarget())) {
                        event.getClicker().sendMessage(quest.getName() + "퀘스트를 클리어 했습니다!");
                        QuestManager.clearQuest(event.getClicker());
                    }
                }
            }
        }
    }
}
