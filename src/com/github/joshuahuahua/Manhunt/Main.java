package com.github.joshuahuahua.Manhunt;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

import com.github.joshuahuahua.Manhunt.listeners.RightClick;



public class Main extends JavaPlugin {



    public static String prefix = "$b$lManhunt$r$8> $7";
    public static List<Player> lobby = new ArrayList<>();
    public static List<Player> hunters = new ArrayList<>();
    //public static List<Player> runners = new ArrayList<>();
    public static Player runner = null;


    @Override
    public void onEnable() {
        PluginManager pluginManager  = getServer().getPluginManager();
        pluginManager.registerEvents(new RightClick(), this);
        getLogger().info("Manhunt Enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("Manhunt Disabled");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("Manhunt") || label.equalsIgnoreCase("mh")) {

            if (args.length == 2 && args[0].equalsIgnoreCase("join") && args[1].equalsIgnoreCase("hunters")) {
                message.sender(sender,"You are now a hunter");
                hunters.add((Player) sender);

            } else if (args.length == 2 && args[0].equalsIgnoreCase("join") && args[1].equalsIgnoreCase("runners")) {
                message.sender(sender,"You are now a runner");
                runner = ((Player) sender);

            } else if (args.length == 1 && args[0].equalsIgnoreCase("start")) {
                message.global("$a$lGame Starting");
                runner.getInventory().clear();
                for (Player player : hunters) {
                    player.getInventory().clear();
                    player.getInventory().addItem(createItem(Material.COMPASS, "Tracker", "Right-click to track runner"));
                }
            }
        }
        return true;
    }
    public static ItemStack createItem(Material material, String name, String lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        ArrayList<String> itemLore = new ArrayList<String>();
        itemLore.add(lore);
        meta.setLore(itemLore);
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }
}