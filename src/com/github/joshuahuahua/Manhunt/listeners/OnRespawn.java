package com.github.joshuahuahua.Manhunt.listeners;

import com.github.joshuahuahua.Manhunt.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;


public class OnRespawn implements Listener {

    @EventHandler
    public void OnRespawn(PlayerRespawnEvent event) {
        if (Main.hunters.contains(event.getPlayer())) {
            event.getPlayer().getInventory().addItem(Main.createItem(Material.COMPASS, "Tracker", "Right-click to track runner"));
        }
    }
}