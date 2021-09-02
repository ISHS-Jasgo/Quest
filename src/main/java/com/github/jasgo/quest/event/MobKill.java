package com.github.jasgo.quest.event;

import com.github.jasgo.levellib.event.MobKillEvent;
import com.github.jasgo.quest.quests.KillMobsQuest;
import com.github.jasgo.quest.util.Quest;
import com.github.jasgo.quest.util.QuestContentType;
import com.github.jasgo.quest.util.QuestManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MobKill implements Listener {
    @EventHandler
    public void onMobKill(MobKillEvent event) {
        if(QuestManager.getQuest(event.getPlayer()) == null)
            return;
        if(QuestManager.getQuest(event.getPlayer()).getContent() == QuestContentType.KillMobs) {
            if(QuestManager.getQuest(event.getPlayer()) instanceof KillMobsQuest) {
                KillMobsQuest quest = (KillMobsQuest) QuestManager.getQuest(event.getPlayer());
                if(event.getMob() == quest.getTarget()) {
                    quest.setCount(quest.getCount() + 1);
                    if(quest.getCount() >= quest.getGoal()) {
                        event.getPlayer().sendMessage(quest.getName() + "퀘스트를 클리어했습니다!");
                        QuestManager.clearQuest(event.getPlayer());
                    }
                }
            }
        }
    }

}
