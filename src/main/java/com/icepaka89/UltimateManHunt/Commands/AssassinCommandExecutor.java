package com.icepaka89.UltimateManHunt.Commands;

import com.icepaka89.UltimateManHunt.Core.UmhManager;
import com.icepaka89.UltimateManHunt.UltimateManHunt;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Implementation of the /assassin command, adds the player name specified in the first argument to
 * the assassins group.
 *
 * @author icepaka89
 */
public class AssassinCommandExecutor implements CommandExecutor {

    /**
     * Reference to the <b>UltimateManHunt</b> plugin main class.
     */
    private final UltimateManHunt plugin;

    /**
     * Reference to the <b>UmhManager</b> instance which manages the plugin's core logic.
     */
    private final UmhManager manager;

    /**
     * @param plugin The UltimateManHunt main plugin class
     * @param manager Manager class, handles all plugin business logic and stores player groups
     */
    public AssassinCommandExecutor(UltimateManHunt plugin, UmhManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    /**
     * Invoked by the plugin when the /assassin command is run. Adds the player name specified by the first argument
     * to the assassins group (if that player is online).
     * @param commandSender
     * @param command
     * @param label
     * @param args Command line args: [0] : player name
     * @return TRUE if command ran successfully, FALSE otherwise.
     */
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        // Returns false (and have bukkit print command usage) if the wrong number of args is specified
        if(args.length < 1) return false;

        // Try to get the specified player name from the server.
        String playerName = args[0];
        Player player = plugin.getServer().getPlayer(playerName);

        // If the player name isn't found, then the player will be null. Print to the user that the player wasn't found.
        if(player == null) {
            plugin.getLogger().info(
                String.format("Player %s was not found!", playerName)
            );
        }

        // Add the player to the assassins group
        manager.addAssassin(player);

        // Print what user was just added to the assassins group.
        plugin.getLogger().info(
            String.format("%s has been added to assassins", playerName)
        );

        Bukkit.broadcastMessage(ChatColor.AQUA + String.format("%s has been added to assassins", playerName));

//        // Notify issuer of command that the player was added
//        Player issuer = (Player) commandSender;
//        issuer.sendMessage(String.format("%s has been added to assassins", playerName));

        // Return true to indicate the command ran successfully.
        return true;
    }
}
