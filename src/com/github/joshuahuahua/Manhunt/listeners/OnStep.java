package com.github.joshuahuahua.Manhunt.listeners;

import com.github.joshuahuahua.Manhunt.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;


public class OnStep implements Listener {

    @EventHandler
    public void onPlayerStep(PlayerMoveEvent event) {
        if (Main.runners.contains(event.getPlayer())) {
            if (event.getPlayer().getWorld().getName().equals("world")) {
                Main.runnerLoaction.put(event.getPlayer(), event.getPlayer().getLocation());
            }
        }
    }
}