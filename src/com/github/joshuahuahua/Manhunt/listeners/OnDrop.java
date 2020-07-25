package com.github.joshuahuahua.Manhunt.listeners;


import com.github.joshuahuahua.Manhunt.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class OnDrop implements Listener {

    @EventHandler
    public void OnPlayerDrop(PlayerDropItemEvent event) {
        if (Main.isRunning) {
            if (Main.hunters.contains(event.getPlayer())) {
                if (event.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals("Tracker")) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
