package com.github.joshuahuahua.Manhunt.listeners;

import com.github.joshuahuahua.Manhunt.Main;
import com.github.joshuahuahua.Manhunt.message;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class OnJoin implements Listener {

    private final Main plugin;

    public OnJoin(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (Main.messageOnJoin) {
                    message.player(event.getPlayer(), "$c$lWelcome to Manhunt");
                    message.player(event.getPlayer(), "$cBy Joshalot");
                    message.player(event.getPlayer(), "$aUse /mh help for available commands!");
                }
            }
        }.runTaskLater(plugin, 5);

    }

}