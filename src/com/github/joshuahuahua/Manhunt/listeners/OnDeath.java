package com.github.joshuahuahua.Manhunt.listeners;

import com.github.joshuahuahua.Manhunt.Main;
import com.github.joshuahuahua.Manhunt.message;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class OnDeath implements Listener {

    private final Main plugin;

    public OnDeath(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (Main.isRunning) {
                    if (Main.runners.contains(event.getEntity())) {
                        event.getEntity().setGameMode(GameMode.SPECTATOR);
                        Main.runners.remove(event.getEntity());
                        Main.lobby.remove(event.getEntity());
                        if (Main.runners.size() == 0) {
                            endGame("hunters");
                        } else {
                            message.global("$a$l" + event.getEntity().getName() + " has died!");
                            message.global("$c$l" + Main.runners.size() + " runners remaining.");
                        }
                    }
                }
            }
        }.runTaskLater(plugin, 5);
    }

    public static void endGame(String winners) {
        Main.isRunning = false;

        message.global("$c$lManhunt has ended");
        message.global("The $a$l" + winners + " $r$7 are the winners!");

        Main.host = null;
        Main.lobby.clear();
        Main.hunters.clear();
        Main.hunterChoice.clear();
        Main.runners.clear();
        Main.runnerLoaction.clear();
    }

}
