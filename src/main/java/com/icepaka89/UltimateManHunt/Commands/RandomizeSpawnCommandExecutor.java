package com.icepaka89.UltimateManHunt.Commands;

import com.icepaka89.UltimateManHunt.Core.UmhManager;
import com.icepaka89.UltimateManHunt.UltimateManHunt;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Implementation of the /randomize-spawn command, generates a random new spawn point for the world. Also sets
 * the random spawn point individually for all players as the bed spawn point.
 *
 * @author icepaka89
 */
public class RandomizeSpawnCommandExecutor implements CommandExecutor {
    public static int MAX_RANDOM_HOPS = 150;
    public static int SPAWN_HOP_RADIUS = 100000;

    /**
     * Reference to the <b>UltimateManHunt</b> plugin main class.
     */
    private final UltimateManHunt plugin;

    /**
     * Reference to the <b>UmhManager</b> instance which manages the plugin's core logic.
     */
    private final UmhManager manager;

    public RandomizeSpawnCommandExecutor(UltimateManHunt plugin, UmhManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        // World names are: "world", "world_nether", "world_the_end"
        var world = plugin.getServer().getWorld("world");

        // Set the world's spawn location to a new random location on land
        boolean bSpawnSetSuccessfully = world.setSpawnLocation(
                getRandomSpawnLocation(world)
        );



        // If spawn was set successfully, then notify players, set their bed spawn points to the new world spawn,
        // and teleport everyone to the new spawn.
        if(bSpawnSetSuccessfully) {
            var spawnLoc = world.getSpawnLocation();
            String message = String.format(
                    "New spawn point set to: %d,%d,%d",
                    spawnLoc.getBlockX(),
                    spawnLoc.getBlockY(),
                    spawnLoc.getBlockZ()
            );

            // Set bed spawns for all players and send a message with the new spawn coordinates.
            plugin.getLogger().info(message);
            manager.getAllPlayers().forEach(player -> {
                player.sendMessage(ChatColor.AQUA +message);
                player.teleport(world.getSpawnLocation());
                player.setBedSpawnLocation(world.getSpawnLocation());
            });
        }
        else {
            plugin.getLogger().info("ERROR, spawn point not set!");
        }

        return true;
    }

    /**
     * Gets the nearest random block within a 150,000 block radius that isn't in the ocean.
     * @param world
     * @return
     */
    private Location getRandomSpawnLocation(World world) {
        var currentSpawnLoc = world.getSpawnLocation();

        // The current API's don't have a method for finding the nearest biome of a specific type, so it's being
        // done here as a loop with limited retry attempts.
        int randX = 0, randZ = 0, randY = 0;
        for(int i = 0; i < MAX_RANDOM_HOPS; i++) {
            // Generates a new random spawn point within 1000 blocks of currentSpawnLoc
            randX = (int) (Math.random() * SPAWN_HOP_RADIUS) + currentSpawnLoc.getBlockX();
            randZ = (int) (Math.random() * SPAWN_HOP_RADIUS) + currentSpawnLoc.getBlockZ();
            randY = world.getHighestBlockYAt(randX, randZ);

            // Set currentSpawnLoc to the new random location
            currentSpawnLoc = new Location(world, randX, randY, randZ);

            if(world.getBiome(randX, randZ) != Biome.OCEAN) break;
        }

        return currentSpawnLoc;
    }

    /**
     * @deprecated
     * @param plugin
     */
    private void listWorlds(UltimateManHunt plugin) {
        plugin.getLogger().info("Listing worlds!");
        var worlds = plugin.getServer().getWorlds();
        worlds.stream().forEach(x -> plugin.getLogger().info(x.getName()));
    }
}
