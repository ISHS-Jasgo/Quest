package com.github.jasgo.quest.event;

import com.github.jasgo.quest.quests.KillMobsQuest;
import com.github.jasgo.quest.util.QuestContentType;
import com.github.jasgo.quest.util.QuestManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MobKill implements Listener {
    @EventHandler
    public void onMobKill(MobKillEvent event) {
        if(QuestManager.getQuest(event.getPlayer()) == null)
            return;
        if(QuestManager.getQuest(event.getPlayer()).getContent() == QuestContentType.KillMobs) {
            if(QuestManager.getQuest(event.getPlayer()) instanceof KillMobsQuest) {
                Player player = event.getPlayer();
                KillMobsQuest quest = (KillMobsQuest) QuestManager.getQuest(player);
                if(event.getMob().equals(quest.getTarget())) {
                    quest.addProgress(player, 1);
                    QuestManager.saveQuests(event.getPlayer());
                }
            }
        }
    }

}
