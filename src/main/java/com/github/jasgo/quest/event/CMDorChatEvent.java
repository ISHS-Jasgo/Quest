package com.github.jasgo.quest.event;

import com.github.jasgo.quest.quests.CMDorChatQuest;
import com.github.jasgo.quest.util.QuestContentType;
import com.github.jasgo.quest.util.QuestManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CMDorChatEvent implements Listener {
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        if(QuestManager.getQuest(event.getPlayer()) == null)
            return;
        if (QuestManager.getQuest(event.getPlayer()).getContent() == QuestContentType.CMDorChat) {
            if (QuestManager.getQuest(event.getPlayer()) instanceof CMDorChatQuest) {
                CMDorChatQuest quest = (CMDorChatQuest) QuestManager.getQuest(event.getPlayer());
                if (quest.isCmd()) {
                    String cmd = event.getMessage();
                    if (quest.getText().equalsIgnoreCase(cmd)) {
                        event.getPlayer().sendMessage(quest.getName() + " 퀘스트를 클리어했습니다!");
                        QuestManager.clearQuest(event.getPlayer());
                    }
                }
            }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if(QuestManager.getQuest(event.getPlayer()) == null)
            return;
        if (QuestManager.getQuest(event.getPlayer()).getContent() == QuestContentType.CMDorChat) {
            if (QuestManager.getQuest(event.getPlayer()) instanceof CMDorChatQuest) {
                CMDorChatQuest quest = (CMDorChatQuest) QuestManager.getQuest(event.getPlayer());
                if (!quest.isCmd()) {
                    String text = event.getMessage();
                    if (quest.getText().equalsIgnoreCase(text)) {
                        event.setCancelled(true);
                        event.getPlayer().sendMessage(quest.getName() + " 퀘스트를 클리어했습니다!");
                        QuestManager.clearQuest(event.getPlayer());
                    }
                }
            }
        }
    }
}
