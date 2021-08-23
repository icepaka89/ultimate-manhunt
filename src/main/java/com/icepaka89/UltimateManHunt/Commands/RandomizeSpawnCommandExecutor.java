package com.icepaka89.UltimateManHunt.Commands;

import com.icepaka89.UltimateManHunt.UltimateManHunt;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Implementation of the /randomize-spawn command, generates a random new spawn point for the world.
 *
 * @author icepaka89
 */
public class RandomizeSpawnCommandExecutor implements CommandExecutor {
    /**
     * Reference to the <b>UltimateManHunt</b> plugin main class.
     */
    private final UltimateManHunt plugin;

    public RandomizeSpawnCommandExecutor(UltimateManHunt plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
//        plugin.getLogger().info("Listing worlds!");
//        var worlds = plugin.getServer().getWorlds();
//        worlds.stream().forEach(x -> plugin.getLogger().info(x.getName()));

        // World names are: "world", "world_nether", "world_the_end"
        var world = plugin.getServer().getWorld("world");

        int randX = (int) (Math.random() * Integer.MAX_VALUE);
        int randZ = (int) (Math.random() * Integer.MAX_VALUE);
        int randY = world.getHighestBlockYAt(randX, randZ);

        world.setSpawnLocation(randX, randY, randZ);
        plugin.getLogger().info(String.format("New spawn point set to: %d,%d,%d", randX, randY, randZ));

        return true;
    }
}
