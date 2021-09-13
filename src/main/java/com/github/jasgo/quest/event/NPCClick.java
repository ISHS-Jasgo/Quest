package com.github.jasgo.quest.event;

import com.github.jasgo.quest.Main;
import com.github.jasgo.quest.conversation.Conversation;
import com.github.jasgo.quest.conversation.ConversationNPC;
import com.github.jasgo.quest.conversation.ConversationNPCLoader;
import com.github.jasgo.quest.quests.*;
import com.github.jasgo.quest.util.Quest;
import com.github.jasgo.quest.util.QuestContentType;
import com.github.jasgo.quest.util.QuestManager;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class NPCClick implements Listener {
    @EventHandler
    public void onClick(NPCRightClickEvent event) {
        if (QuestManager.npcQuest.containsKey(event.getNPC().getUniqueId())) {
            if (QuestManager.getQuest(event.getClicker()) == null) {
                if (QuestManager.npcQuest.get(event.getNPC().getUniqueId()).getStart() != null) {
                    boolean getQuest = QuestManager.giveQuest(event.getClicker(), QuestManager.npcQuest.get(event.getNPC().getUniqueId()));
                    if (getQuest) {
                        (new BukkitRunnable() {
                            @Override
                            public void run() {
                                event.getClicker().sendMessage(QuestManager.getQuest(event.getClicker()).getName() + " 퀘스트를 받았습니다!");
                            }
                        }).runTaskLaterAsynchronously(Main.getPlugin(Main.class), QuestManager.getQuest(event.getClicker()).getStart().getDelay() * (QuestManager.getQuest(event.getClicker()).getStart().getLines().size() + 1));
                    } else {
                        if (QuestManager.getClearQuests(event.getClicker()).contains(QuestManager.npcQuest.get(event.getNPC().getUniqueId()))) {
                            event.getClicker().sendMessage("당신은 이미 이 퀘스트를 클리어 했습니다!");
                        }
                    }
                }
            } else {
                if (QuestManager.getQuest(event.getClicker()).equals(QuestManager.npcQuest.get(event.getNPC().getUniqueId()))) {
                    if (QuestManager.npcQuest.get(event.getNPC().getUniqueId()).getEnd() != null) {
                        Quest quest = QuestManager.getQuest(event.getClicker());
                        if (quest instanceof KillMobsQuest) {
                            KillMobsQuest killMobsQuest = (KillMobsQuest) quest;
                            if (killMobsQuest.isClear(event.getClicker())) {
                                Player player = event.getClicker();
                                player.sendMessage(quest.getName() + " 퀘스트를 클리어했습니다!");
                                killMobsQuest.setProgress(player, 0);
                                QuestManager.clearQuest(player);
                            } else {
                                QuestInfo info = new QuestInfo(killMobsQuest, event.getClicker());
                                event.getClicker().openInventory(info.getQuestGui());
                            }
                        } else if (quest instanceof NPCInteractQuest) {
                            NPCInteractQuest npcInteractQuest = (NPCInteractQuest) quest;
                            if (npcInteractQuest.isClear(event.getClicker())) {
                                Player player = event.getClicker();
                                player.sendMessage(quest.getName() + " 퀘스트를 클리어했습니다!");
                                npcInteractQuest.removeClear(player);
                                QuestManager.clearQuest(player);
                            } else {
                                QuestInfo info = new QuestInfo(npcInteractQuest, event.getClicker());
                                event.getClicker().openInventory(info.getQuestGui());
                            }
                        } else if (quest instanceof GatherItemQuest) {
                            GatherItemQuest gatherItemQuest = (GatherItemQuest) quest;
                            if (gatherItemQuest.isClear(event.getClicker())) {
                                Player player = event.getClicker();
                                player.sendMessage(quest.getName() + " 퀘스트를 클리어했습니다!");
                                QuestManager.clearQuest(player);
                            } else {
                                QuestInfo info = new QuestInfo(gatherItemQuest, event.getClicker());
                                event.getClicker().openInventory(info.getQuestGui());
                            }
                        } else if (quest instanceof CMDorChatQuest) {
                            CMDorChatQuest cmDorChatQuest = (CMDorChatQuest) quest;
                            QuestInfo info = new QuestInfo(cmDorChatQuest, event.getClicker());
                            event.getClicker().openInventory(info.getQuestGui());
                        }
                    }
                } else {
                    event.getClicker().sendMessage("당신은 이미 다른 퀘스트를 진행중입니다!");
                }
            }
        } else {
            if (QuestManager.getQuest(event.getClicker()) == null)
                return;
            if (QuestManager.getQuest(event.getClicker()).getContent() == QuestContentType.NPCInteract) {
                if (QuestManager.getQuest(event.getClicker()) instanceof NPCInteractQuest) {
                    NPCInteractQuest quest = (NPCInteractQuest) QuestManager.getQuest(event.getClicker());
                    if (ConversationNPCLoader.conversationNPCList.containsKey(event.getNPC().getUniqueId())) {
                        if (quest.getTarget().equals(event.getNPC().getUniqueId())) {
                            quest.setClear(event.getClicker());
                            ConversationNPCLoader.conversationNPCList.get(event.getNPC().getUniqueId()).getConversation().start(event.getClicker());
                        }
                    }
                }
            }
        }
    }
}
