package com.github.joshuahuahua.Manhunt.listeners;


import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.github.joshuahuahua.Manhunt.Main;
import com.github.joshuahuahua.Manhunt.message;


public class RightClick implements Listener {

    @EventHandler
    public void onPlayerUse(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        Action action = event.getAction();
        if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
            if (Main.isRunning) {
                if(player.getInventory().getItemInMainHand().getType() == Material.COMPASS && player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("Tracker")){
                    if (!player.isSneaking()) {
                        if (Main.hunterChoice != null) {
                            Main.updateCompass(player);
                        }
                    } else {
                        //GUI
                        Main.selectInv(event.getPlayer(), "Runners");
                    }
                }
            }
        }
    }
}