package com.github.joshuahuahua.Manhunt.listeners;

import org.bukkit.entity.EnderDragon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import com.github.joshuahuahua.Manhunt.message;

public class OnDragonDeath implements Listener {

    @EventHandler
    public void onEnderDragonDeath(EntityDeathEvent entity){
        if (entity.getEntity() instanceof EnderDragon){
            OnDeath.endGame("runners");
        }
    }
}