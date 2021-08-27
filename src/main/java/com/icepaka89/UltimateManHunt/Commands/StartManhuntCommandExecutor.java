package com.icepaka89.UltimateManHunt.Commands;

import com.icepaka89.UltimateManHunt.Core.UmhManager;
import com.icepaka89.UltimateManHunt.UltimateManHunt;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Implementation of the /start-manhunt command. Teleports all players to their starting positions! Currently this
 * only works in the overworld.
 */
public class StartManhuntCommandExecutor implements CommandExecutor {

    /**
     * Reference to the <b>UltimateManHunt</b> plugin main class.
     */
    private final UltimateManHunt plugin;

    /**
     * Reference to the <b>UmhManager</b> instance which manages the plugin's core logic.
     */
    private final UmhManager manager;

    public StartManhuntCommandExecutor(UltimateManHunt plugin, UmhManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(manager.getAssassins().size() <= 0 || manager.getSpeedrunners().size() <= 0) {
            var player = (Player) commandSender;
            player.sendMessage(ChatColor.RED + "Can't start until there's players in both groups! Use " +
                    "/manhunt-groups to see a list of players in each group.");
            return true;
        }

        // TODO: Also check if all players are in the same world?

        var world = plugin.getServer().getWorld("world");

        var spawnLocation = new Location(
                world,
                world.getSpawnLocation().getX(),
                world.getSpawnLocation().getY(),
                world.getSpawnLocation().getZ()
        );
        var assassinStartLocation = world.getSpawnLocation().add(
                manager.getStartingDistance(),
                0,
                0
        );
        assassinStartLocation.setY(world.getHighestBlockYAt(assassinStartLocation));

        manager.getSpeedrunners().forEach(p -> {
            p.teleport(spawnLocation);
        });

        manager.getAssassins().forEach(p -> {
            p.teleport(assassinStartLocation);
        });

        Bukkit.broadcastMessage(ChatColor.AQUA + String.format(
                "Speedrunners start! Assassin must wait for %d seconds!", manager.getCountdownTime()
        ));

        var countdownTimerTask = new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.broadcastMessage(ChatColor.AQUA + "Assassins start! Good luck!");
            }
        };

        countdownTimerTask.runTaskLater(plugin, manager.getCountdownTime() * 20L);

        return true;
    }
}
