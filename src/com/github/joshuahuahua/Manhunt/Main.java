package com.github.joshuahuahua.Manhunt;

import com.github.joshuahuahua.Manhunt.listeners.*;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Main extends JavaPlugin {



    public static String prefix = "$7[$bManhunt$7] ";
    public static Player host = null;
    public static boolean isRunning = false;
    static int freezeTime = 20; // Seconds
    public static boolean freeze = false;
    static int freezeTimer;
    int currentTime;
    public static boolean messageOnJoin = true;

    public static List<Player> lobby = new ArrayList<>();
    public static List<Player> hunters = new ArrayList<>();
    public static List<Player> runners = new ArrayList<>();

    // Game Rules
    public static HashMap<String, Boolean> getGameRules() {
        HashMap<String, Boolean> gameRules = new HashMap<String, Boolean>();
        gameRules.put("viewRunnerHeath", false);
        gameRules.put("viewHunterHeath", false);
        return gameRules;
    }
    public static HashMap<String, Boolean> gameRules = Main.getGameRules();

    //############################## runnerLoaction ################################
    public static HashMap<Player, Location> getRunnerLoaction() {
        HashMap<Player, Location> runnerLoaction = new HashMap<Player, Location>();
        return runnerLoaction;
    }
    public static HashMap<Player, Location> runnerLoaction = Main.getRunnerLoaction();

    //############################## hunterChoice ################################
    public static HashMap<Player, Player> getHunterChoice() {
        HashMap<Player, Player> hunterChoice = new HashMap<Player, Player>();
        return hunterChoice;
    }
    public static HashMap<Player, Player> hunterChoice = Main.getHunterChoice();



    @Override
    public void onEnable() {
        PluginManager pluginManager  = getServer().getPluginManager();
        pluginManager.registerEvents(new OnDeath(this), this);
        pluginManager.registerEvents(new OnDragonDeath(), this);
        pluginManager.registerEvents(new OnRespawn(), this);
        pluginManager.registerEvents(new OnStep(), this);
        pluginManager.registerEvents(new RightClick(), this);
        pluginManager.registerEvents(new PlayerClickInventory(), this);
        pluginManager.registerEvents(new OnJoin(this), this);
        pluginManager.registerEvents(new OnLeave(this), this);
        pluginManager.registerEvents(new OnDrop(), this);
        getLogger().info("Manhunt Enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("Manhunt Disabled");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("Manhunt") || label.equalsIgnoreCase("mh")) {


            //############################## /mh test ################################
            if (args.length == 1 && args[0].equalsIgnoreCase("test")) {
                message.global("hunters: " + hunters.toString());
                message.global("runners: " + runners.toString());
                message.global("runnerLoaction: " + runnerLoaction.toString());
                message.global("hunterChoice: " + hunterChoice.toString());
                return true;
            }

            
            //############################## /mh help ################################
            if (args.length == 0 || (args.length == 1 && args[0].equalsIgnoreCase("help"))) {
                message.sender(sender,"$e------------ $fHelp: Index (1/1) $e------------");
                message.sender(sender,"$6Aliases: $f/manhunt, /mh");
                message.sender(sender,"$6/mh create: $fCreates a new Manhunt Lobby");
                message.sender(sender,"$6/mh join: $fJoins available Manhunt lobby");
                message.sender(sender,"$6/mh hunter: $fJoin the 'runners'");
                message.sender(sender,"$6/mh runner: $fJoin the 'hunters'");
                message.sender(sender,"$6/mh leave: $fLeaves current lobby");
                message.sender(sender,"$6/mh list: $fLists all players in current lobby");
                message.sender(sender,"$6/mh timeset <time>: $fSets runner's start time");
                message.sender(sender,"$6/mh start: $fStarts Manhunt match");
                message.sender(sender,"$6/mh stop: $fStops Manhunt match");
                return true;
            }


            //############################## /mh create ################################
            if (args.length == 1 && (args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("host"))) {
                if (host == null) {
                    host = (Player) sender;
                    lobby.add(host);
                    message.global("$a$l" + host.getName() + " $r$7is has created a lobby!");
                    message.global("Use /mh join to join!");
                } else {
                    message.sender(sender,"$a$l" + host.getName() + " $r$cis already hosting Manhunt!");
                    message.sender(sender,"Use /mh join to join!");
                }
                return true;
            }


            //############################## /mh join ################################
            if (args.length == 1 && args[0].equalsIgnoreCase("join")) {
                if (host != null) {
                    if (!isRunning) {
                        if (!lobby.contains((Player) sender)) {
                            lobby.add((Player) sender);
                            message.global("$a$l" + sender.getName() + " $r$7has joined the lobby!");
                        } else {
                            message.sender(sender,"$cYou are already in a lobby!");
                        }
                    } else {
                        message.sender(sender,"$cThere is already a game in progress!");
                    }
                } else {
                    message.sender(sender,"$cThere are no available lobbies!");
                    message.sender(sender,"Use /mh create to host a lobby!");
                }
                return true;
            }


            //############################## /mh list ################################
            if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
                if (lobby.contains((Player) sender)) {
                    message.sender(sender,"$lCurrent players in your lobby:");
                    for (Player player : lobby) {
                        String role = "Unassigned";
                        if (hunters.contains(player))
                            role = "Hunter";
                        else if (runners.contains(player)) {
                            role = "Runner";
                        }
                        message.sender(sender, "$l$a" + player.getName() + ": $r$7Role: " + role);
                    }
                } else {
                    message.sender(sender,"$cYou are not in a lobby!");
                    message.sender(sender,"Use /mh create to create one or /mh join to join an existing one!");
                }
                return true;
            }


            //############################## /mh timeset <num> ################################
            if (args.length == 2 && args[0].equalsIgnoreCase("timeset")) {
                if (host != null) {
                    if (sender.getName().equals(host.getName())) {
                        if (!isRunning) {
                            freezeTime = Integer.parseInt(args[1]);
                            message.sender(sender,"Set time to$e " + freezeTime);
                        } else {
                            message.sender(sender,"$cYou can not change this mid-game!");
                        }
                    } else {
                        sender.sendMessage("$cOnly the host can do that!");
                    }
                } else {
                    message.sender(sender,"$cThere are no available lobbies!");
                    message.sender(sender,"Use /mh create to host a lobby!");
                }
                return true;
            }


            //############################## /mh leave ################################
            if (args.length == 1 && args[0].equalsIgnoreCase("leave")) {
                if (host != null) {
                    if (!isRunning) {
                        if (sender.getName().equals(host.getName())) {
                            message.global("$a$l" + host.getName() + " $r$7has closed the lobby!");
                            for (Player player : lobby) {
                                message.player(player, "$cYou have been removed from the lobby!");
                            }
                            host = null;
                            lobby.clear();
                            hunters.clear();
                            hunterChoice.clear();
                            runners.clear();
                            runnerLoaction.clear();
                            return true;
                        }
                        if (lobby.contains((Player) sender)) {
                            lobby.remove((Player) sender);
                            message.global("$a$l" + sender.getName() + " $r$7has left the lobby!");
                        } else {
                            message.sender(sender, "$cYou are not in a lobby!");
                            message.sender(sender, "Use /mh create to create one or /mh join to join an existing one!");
                        }
                    } else {
                        message.sender(sender, "$cYou can't leave an active game!");
                    }
                } else {
                    message.sender(sender,"$cThere are no available lobbies!");
                    message.sender(sender,"Use /mh create to host a lobby!");
                }
                return true;
            }


            //############################## /mh hunter ################################
            if (args.length == 1 && args[0].equalsIgnoreCase("hunter")) {
                if (host != null) {
                    if (!isRunning) {
                        if (lobby.contains((Player) sender)) {
                            if (!hunters.contains((Player) sender)) {
                                hunters.add((Player) sender);
                                runners.remove((Player) sender);
                            }
                            message.sender(sender, "You are now a hunter");
                            message.global("$l$a" + sender.getName() + " $r$7is now a hunter");
                        } else {
                            message.sender(sender, "$cYou are not in a lobby!");
                            message.sender(sender, "Use /mh create to create one or /mh join to join an existing one!");
                        }
                    } else {
                        message.sender(sender, "$cYou can't leave an active game!");
                    }
                } else {
                    message.sender(sender, "$cThere are no available lobbies!");
                    message.sender(sender, "Use /mh create to host a lobby!");
                }
                return true;
            }


            //############################## /mh runner ################################
            if (args.length == 1 && args[0].equalsIgnoreCase("runner")) {
                if (host != null) {
                    if (!isRunning) {
                        if (lobby.contains((Player) sender)) {
                            if (!runners.contains((Player) sender)) {
                                runners.add((Player) sender);
                                hunters.remove((Player) sender);
                            }
                            message.sender(sender, "You are now a runner");
                            message.global("$l$a" + sender.getName() + " $r$7is now a runner");

                        } else {
                            message.sender(sender, "$cYou are not in a lobby!");
                            message.sender(sender, "Use /mh create to create one or /mh join to join an existing one!");
                        }
                    } else {
                        message.sender(sender, "$cYou can't leave an active game!");
                    }
                } else {
                    message.sender(sender, "$cThere are no available lobbies!");
                    message.sender(sender, "Use /mh create to host a lobby!");
                }
                return true;
            }


            //############################## /mh start ################################
            if (args.length == 1 && args[0].equalsIgnoreCase("start")) {
                if (host != null) {
                    if (sender.getName().equals(host.getName())) {
                        if (!isRunning) {
                            if (hunters.size() > 0 && runners.size() > 0) {

                                isRunning = true;

                                for (Player player : lobby) {
                                    player.setGameMode(GameMode.SURVIVAL);
                                    player.getInventory().clear();
                                    player.setHealth(20);
                                    player.setFoodLevel(20);
                                    player.teleport(host.getLocation());
                                }
                                for (Player runner : runners) {
                                    Main.runnerLoaction.put(runner, runner.getLocation());
                                    message.title(runner,"$a$lManhunt Started", "$aYou have $c" + freezeTime + "$a seconds!", 10,40,10);
                                }
                                for (Player hunter : hunters) {
                                    hunter.getInventory().addItem(createItem(Material.COMPASS, "Tracker", "Right-click to track current runner", "Shift Right-click to select runner"));
                                    hunterChoice.put(hunter, runners.get(0));
                                    updateCompass(hunter);
                                    message.title(hunter,"$a$lManhunt Started", "$aThe runners have $c" + freezeTime + "$a seconds!", 10,20,10);
                                }

                                message.global("$a$lManhunt Started");

                                freeze = true;
                                currentTime = freezeTime;

                                BukkitScheduler scheduler = getServer().getScheduler();


                                freezeTimer = scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
                                    @Override
                                    public void run() {
                                        currentTime-=1;

                                        for (int i : new int[]{1, 2, 3}) {
                                            if (i == currentTime) {
                                                for (Player hunter : hunters) {
                                                    message.title(hunter, "$a" + i, "", 5, 0, 5);
                                                }
                                            }
                                        }

                                        if (currentTime == 0) {
                                            freeze = false;

                                            for (Player hunter : hunters) {
                                                message.title(hunter,"$c$lHUNT!", "", 10,10,10);
                                            }

                                            Bukkit.getScheduler().cancelTask(freezeTimer);
                                        }
                                    }
                                }, 20, 20);

                            } else {
                                message.sender(sender, "$cYou need at least 1 hunter and 1 runner!");
                            }
                        } else {
                            message.sender(sender, "$cGame already in progress!");
                        }
                    } else {
                        message.sender(sender, "$cOnly the host can do that!");
                    }
                } else {
                    message.sender(sender, "$cThere is no one hosting Manhunt!");
                    message.sender(sender, "Use /ds create to host a lobby!");
                }
                return true;
            }


            //############################## /ds stop ################################
            if (args.length == 1 && args[0].equalsIgnoreCase("stop")) {
                if (host != null) {
                    if (sender.getName().equals(host.getName())) {
                        if (isRunning) {
                            isRunning = false;
                            Bukkit.getScheduler().cancelTask(freezeTimer);
                            message.global("$c$lManhunt Stopped");
                        } else {
                            message.sender(sender,"$cNo active Manhunt!");
                        }
                    } else {
                        message.sender(sender,"$cOnly the host can do that!");
                    }
                }
                return true;
            }


            message.sender(sender,"$cInvalid command!");
            message.sender(sender,"Use /mh help for a list of available commands.");
            return true;
        } else {
            return false;
        }
    }
    public static ItemStack createItem(Material material, String name, String lore1, String lore2) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        ArrayList<String> itemLore = new ArrayList<String>();
        itemLore.add(lore1);
        itemLore.add(lore2);
        meta.setLore(itemLore);
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack runnerListItem(Player player) {

        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwningPlayer(player);
        meta.setDisplayName(player.getName());
        skull.setItemMeta(meta);
        return skull;
    }

    public static void selectInv(Player player, String inventory) {

        if (inventory.equalsIgnoreCase("Runners")) {

            Inventory runnersInv = Bukkit.createInventory(null, (int) Math.ceil(runners.size()/9.0)*9, "Runners");

            for (Player currentRunner : runners) {
                runnersInv.addItem(runnerListItem(currentRunner));
            }

            player.openInventory(runnersInv);
        }
    }

    public static void updateCompass(Player player) {
        player.setCompassTarget(Main.runnerLoaction.get(Main.hunterChoice.get(player)));
        message.player(player,"Now tracking: $l$a" + Main.hunterChoice.get(player).getName());
    }
}