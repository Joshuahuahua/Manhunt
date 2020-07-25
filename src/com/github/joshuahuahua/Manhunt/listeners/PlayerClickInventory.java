package com.github.joshuahuahua.Manhunt.listeners;

import com.github.joshuahuahua.Manhunt.Main;
import com.github.joshuahuahua.Manhunt.message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PlayerClickInventory implements Listener {

    @EventHandler
    public void onPlayerClickInventory(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equals("Runners")) {
            event.setCancelled(true);
            for (Player currentRunner : Main.runners) {
                if (currentRunner == Bukkit.getServer().getPlayer(event.getCurrentItem().getItemMeta().getDisplayName())) {
                    player.closeInventory();
                    Main.hunterChoice.put(player, currentRunner);
                    Main.updateCompass(player);
                    return;
                }
            }
            message.player(player, "$cThat player does not exist!");
        }
    }
}