package com.icepaka89.UltimateManHunt.Commands;

import com.icepaka89.UltimateManHunt.Core.UmhManager;
import com.icepaka89.UltimateManHunt.UltimateManHunt;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;

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
                world.getHighestBlockYAt(world.getSpawnLocation()) + 1,
                world.getSpawnLocation().getZ()
        );
        var assassinStartLocation = world.getSpawnLocation().add(
                manager.getStartingDistance(),
                0,
                0
        );
        assassinStartLocation.setY(world.getHighestBlockYAt(assassinStartLocation) + 1);

        manager.getSpeedrunners().forEach(p -> {
            p.teleport(spawnLocation);
            p.getInventory().clear();
            p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
            p.setFoodLevel(20);

            // If assassin freeze is enabled, give the speedrunner a diamond sword
            // with a special name. This sword can freeze assassin it hits for a
            // few seconds.
            if(manager.isFreezeAssassinEnabled()) {
                ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
                var swordMeta = sword.getItemMeta();
                swordMeta.setDisplayName("Ultimate Manhunt Freeze Sword");
                swordMeta.setLore(Arrays.asList("Uses: 2", "Freezes assassin for 15 seconds when used!"));
                sword.setItemMeta(swordMeta);

                p.getInventory().addItem(sword);
            }
        });

        manager.getAssassins().forEach(p -> {
            p.teleport(assassinStartLocation);
            p.getInventory().clear();
            p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
            p.setFoodLevel(20);

            // Give new assassin a special compass
            ItemStack compass = new ItemStack(Material.COMPASS);
            var compassMeta = compass.getItemMeta();
            compassMeta.setDisplayName("Ultimate Manhunt Compass");
            compass.setItemMeta(compassMeta);
            p.getInventory().addItem(compass);
        });

        Bukkit.broadcastMessage(ChatColor.AQUA + String.format(
                "Speedrunners start! Assassin must wait for %d seconds!", manager.getCountdownTime()
        ));

        // TODO: Add another notification for when half time has elapsed?

        var countdownTimerTask = new BukkitRunnable() {
            @Override
            public void run() {
                manager.setIsCountdownTimerRunning(false);
                manager.startManhunt();
                Bukkit.broadcastMessage(ChatColor.AQUA + "Assassins start! Good luck!");
            }
        };

        manager.setIsCountdownTimerRunning(true);
        countdownTimerTask.runTaskLater(plugin, manager.getCountdownTime() * 20L);

        return true;
    }
}
