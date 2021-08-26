package com.icepaka89.UltimateManHunt.Commands;

import com.icepaka89.UltimateManHunt.Core.UmhManager;
import com.icepaka89.UltimateManHunt.UltimateManHunt;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Implementation of the /starting-distance command, sets the starting distance that the assassin will be placed
 * from the speedrunner when the game starts
 */
public class StartingDistanceCommandExecutor implements CommandExecutor {

    /**
     * Reference to the <b>UltimateManHunt</b> plugin main class.
     */
    private final UltimateManHunt plugin;

    /**
     * Reference to the <b>UmhManager</b> instance which manages the plugin's core logic.
     */
    private final UmhManager manager;

    public StartingDistanceCommandExecutor(UltimateManHunt plugin, UmhManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    /**
     *
     * @param commandSender
     * @param command
     * @param label
     * @param args Command line args: [0] : Starting distance (in blocks)
     * @return
     */
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        // Returns false (and have bukkit print command usage) if the wrong number of args is specified
        if(args.length < 1) return false;

        try {
            int startingDistance = Integer.parseInt(args[0]);
            manager.setStartingDistance(startingDistance);

            Bukkit.broadcastMessage(ChatColor.AQUA + String.format("Assassin starting distance set to %d", startingDistance));
        }
        catch(NumberFormatException exception) {
            return false;
        }

        return true;
    }
}
