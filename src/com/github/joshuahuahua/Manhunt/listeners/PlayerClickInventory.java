package com.github.joshuahuahua.Manhunt.listeners;

import com.github.joshuahuahua.Manhunt.Main;
import com.github.joshuahuahua.Manhunt.message;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PlayerClickInventory implements Listener {

    @EventHandler
    public void onPlayerClickInventory(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        message.global("CLCICK");
        if (event.getView().getTitle().equals("Runners")) {
            message.global("CLCICK");
            event.setCancelled(true);
            for (Player currentRunner : Main.runners) {
                if (player.getName().equals(event.getCurrentItem().getItemMeta().getDisplayName())) {
                    Main.hunterChoice.put((Player) event.getWhoClicked(), currentRunner);
                    player.closeInventory();
                }
            }
            message.player((Player) event.getWhoClicked(), "Targeting " + event.getCurrentItem().getItemMeta().getDisplayName());
        }
    }
}