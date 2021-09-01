package com.github.jasgo.quest.event;

import com.github.jasgo.quest.quests.CMDorChatQuest;
import com.github.jasgo.quest.util.Quest;
import com.github.jasgo.quest.util.QuestContentType;
import com.github.jasgo.quest.util.QuestManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;

import java.util.Arrays;

public class CMDorChatEvent implements Listener {
    @EventHandler
    public void onCommand(PlayerCommandSendEvent event) {
        if (QuestManager.getQuest(event.getPlayer()).getContent() == QuestContentType.CMDorChat) {
            if (QuestManager.getQuest(event.getPlayer()) instanceof CMDorChatQuest) {
                CMDorChatQuest quest = (CMDorChatQuest) QuestManager.getQuest(event.getPlayer());
                if (quest.isCmd()) {
                    String cmd = Arrays.toString(event.getCommands().toArray())
                            .replace("[", "")
                            .replace("]", "")
                            .replace(",", "");
                    if (quest.getText().equalsIgnoreCase(cmd)) {
                        QuestManager.clearQuest(event.getPlayer());
                    }
                }
            }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (QuestManager.getQuest(event.getPlayer()).getContent() == QuestContentType.CMDorChat) {
            if (QuestManager.getQuest(event.getPlayer()) instanceof CMDorChatQuest) {
                CMDorChatQuest quest = (CMDorChatQuest) QuestManager.getQuest(event.getPlayer());
                if (!quest.isCmd()) {
                    String text = event.getMessage();
                    if (quest.getText().equalsIgnoreCase(text)) {
                        QuestManager.clearQuest(event.getPlayer());
                    }
                }
            }
        }
    }
}
