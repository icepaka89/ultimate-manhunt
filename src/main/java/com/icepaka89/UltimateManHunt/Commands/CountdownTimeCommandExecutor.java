package com.icepaka89.UltimateManHunt.Commands;

import com.icepaka89.UltimateManHunt.Core.UmhManager;
import com.icepaka89.UltimateManHunt.UltimateManHunt;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CountdownTimeCommandExecutor implements CommandExecutor {

    /**
     * Reference to the <b>UltimateManHunt</b> plugin main class.
     */
    private final UltimateManHunt plugin;

    /**
     * Reference to the <b>UmhManager</b> instance which manages the plugin's core logic.
     */
    private final UmhManager manager;

    public CountdownTimeCommandExecutor(UltimateManHunt plugin, UmhManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    /**
     *
     * @param commandSender
     * @param command
     * @param label
     * @param args Command line args: [0] : Countdown time (in seconds)
     * @return
     */
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        try {
            // Make sure the countdown time was specified
            if(args.length == 0) {
                return false;
            }

            manager.setCountdownTime(
                    Integer.parseInt(args[0])
            );

            Bukkit.broadcastMessage(ChatColor.AQUA + String.format("Countdown time set to %s", args[0]));

            return true;
        }
        catch(Exception ex) {
            plugin.getLogger().warning(ex.getMessage());
            return false;
        }

    }
}
