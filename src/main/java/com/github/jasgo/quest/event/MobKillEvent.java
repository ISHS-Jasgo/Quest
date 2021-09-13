package com.github.jasgo.quest.event;

import com.github.jasgo.levellib.mobs.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class MobKillEvent extends PlayerEvent {

    private final Mob mob;
    private final static HandlerList HANDLERS = new HandlerList();

    public MobKillEvent(Player who, Mob mob) {
        super(who);
        this.mob = mob;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public Mob getMob() {
        return mob;
    }
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
