package com.github.joshuahuahua.Manhunt.listeners;


import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.github.joshuahuahua.Manhunt.Main;
import com.github.joshuahuahua.Manhunt.message;


import java.util.Locale;
import java.util.Map;


public class RightClick implements Listener {

    @EventHandler
    public void onPlayerUse(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        Action action = event.getAction();
        if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
            if (Main.isRunning) {
                if(player.getInventory().getItemInMainHand().getType() == Material.COMPASS && player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("Tracker")){
                    if (!player.isSneaking()) {
                        message.global("NOT SNEAKING");
                        if (Main.hunterChoice != null) {
                            message.global("Attempt to set compass");
                            message.global(Main.getHunterChoice().get(player).getName());
                            message.global(Main.getRunnerLoaction().get(Main.getHunterChoice().get(player)).toString());
                            player.setCompassTarget(Main.getRunnerLoaction().get(player));
                            //player.setCompassTarget(Main.getRunnerLoaction().get(Main.getHunterChoice().get(event.getPlayer())));
                            message.global("Now tracking: " + player.getName());
                        }
                    } else {
                        //GUI
                        message.global("SNEAKING");
                        Main.selectInv(event.getPlayer(), "Runners");
                    }
                }
            }
        }
    }
}