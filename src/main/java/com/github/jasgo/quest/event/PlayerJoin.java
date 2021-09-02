package com.github.jasgo.quest.event;

import com.github.jasgo.quest.util.QuestManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        QuestManager.initQuests(event.getPlayer());
    }
}
