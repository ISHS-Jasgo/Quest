package com.github.jasgo.quest.event;

import io.lumine.xikage.mythicmobs.MythicMobs;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class OffHandEvent implements Listener {
    @EventHandler
    public void onSwapItem(PlayerSwapHandItemsEvent event) {
        event.setCancelled(true);
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(event.getInventory().getType().equals(InventoryType.CRAFTING)) {
            if(event.getSlot() == 40) {
                event.setCancelled(true);
            }
        }
    }
}
