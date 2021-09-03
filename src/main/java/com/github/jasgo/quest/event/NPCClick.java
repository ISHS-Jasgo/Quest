package com.github.jasgo.quest.event;

import com.github.jasgo.quest.Main;
import com.github.jasgo.quest.conversation.Conversation;
import com.github.jasgo.quest.quests.NPCInteractQuest;
import com.github.jasgo.quest.util.QuestContentType;
import com.github.jasgo.quest.util.QuestManager;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class NPCClick implements Listener {
    @EventHandler
    public void onClick(NPCRightClickEvent event) {
        if(QuestManager.npcQuest.containsKey(event.getNPC().getUniqueId())) {
            if(QuestManager.npcQuest.get(event.getNPC().getUniqueId()).getStart() != null) {
                boolean getQuest = QuestManager.giveQuest(event.getClicker(), QuestManager.npcQuest.get(event.getNPC().getUniqueId()));
                if (getQuest) {
                    Conversation conversation = QuestManager.npcQuest.get(event.getNPC().getUniqueId()).getStart();
                    conversation.start(event.getClicker());
                    (new BukkitRunnable() {
                        @Override
                        public void run() {
                            event.getClicker().getInventory().setItemInOffHand(new ItemStack(Material.AIR));
                            event.getClicker().sendMessage(QuestManager.getQuest(event.getClicker()).getName() + " 퀘스트를 받았습니다!");
                        }
                    }).runTaskLaterAsynchronously(Main.getPlugin(Main.class), conversation.getDelay() * (conversation.getLines().size() + 1));
                } else {
                    if (QuestManager.getClearQuests(event.getClicker()).contains(QuestManager.npcQuest.get(event.getNPC().getUniqueId()))) {
                        event.getClicker().sendMessage("당신은 이미 이 퀘스트를 클리어 했습니다!");
                    } else {
                        event.getClicker().sendMessage("당신은 이미 다른 퀘스트를 진행중입니다!");
                    }
                }
            }
        } else {
            if(QuestManager.getQuest(event.getClicker()) == null)
                return;
            if (QuestManager.getQuest(event.getClicker()).getContent() == QuestContentType.NPCInteract) {
                if(QuestManager.getQuest(event.getClicker()) instanceof NPCInteractQuest) {
                    NPCInteractQuest quest = (NPCInteractQuest) QuestManager.getQuest(event.getClicker());
                    if(event.getNPC().getUniqueId().equals(quest.getTarget())) {
                        event.getClicker().sendMessage(quest.getName() + " 퀘스트를 클리어 했습니다!");
                        QuestManager.clearQuest(event.getClicker());
                    }
                }
            }
        }
    }
}
