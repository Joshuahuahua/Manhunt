package com.github.joshuahuahua.Manhunt.listeners;

import com.github.joshuahuahua.Manhunt.Main;
import com.github.joshuahuahua.Manhunt.message;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class OnLeave implements Listener {

    private final Main plugin;

    public OnLeave(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (Main.isRunning) {
                    message.global("$6" + event.getPlayer().getName() + " $ahas left the Manhunt!");
                    Main.lobby.remove(event.getPlayer());
                    Main.runners.remove(event.getPlayer());
                    Main.hunters.remove(event.getPlayer());
                    if (Main.runners.size() == 0) {
                        OnDeath.endGame("hunters");
                    } else if (Main.hunters.size() == 0) {
                        OnDeath.endGame("runners");
                    }
                    message.global("$c$l" + Main.lobby.size() + " players remaining.");
                }
            }
        }.runTaskLater(plugin, 5);
    }
}
