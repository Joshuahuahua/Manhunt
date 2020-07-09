package com.github.joshuahuahua.Manhunt.listeners;


import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.github.joshuahuahua.Manhunt.message;
import com.github.joshuahuahua.Manhunt.Main;


public class RightClick implements Listener {

    //@EventHandler(priority= EventPriority.HIGH)
    @EventHandler
    public void onPlayerUse(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        Action action = event.getAction();

        if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
            if(player.getInventory().getItemInMainHand().getType() == Material.COMPASS && player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("Tracker")){
                message.global("poggers");
                player.setCompassTarget(Main.runner.getLocation());






            }
        }
    }
}