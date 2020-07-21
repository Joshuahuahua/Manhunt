package com.github.joshuahuahua.Manhunt;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class message {

    public static void global(String message) {
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('$', Main.prefix + message));
    }

    public static void sender(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('$',Main.prefix + message));
    }

    public static void player(Player player, String message) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('$',Main.prefix + message));
    }

    public static void title(Player player, String title, String subtitle, Integer fadeIn, Integer hold, Integer fadeOut) {
        player.sendTitle(ChatColor.translateAlternateColorCodes('$', title), ChatColor.translateAlternateColorCodes('$',subtitle), fadeIn,hold,fadeOut);
    }
}