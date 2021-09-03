package com.icepaka89.UltimateManHunt.Commands;

import com.icepaka89.UltimateManHunt.Core.UmhManager;
import com.icepaka89.UltimateManHunt.UltimateManHunt;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * @author : Daniel Gomm
 * @since : 9/3/21, Fri
 */
public class ToggleDistanceReportingCommandExecutor implements CommandExecutor {

    /**
     * Reference to the <b>UltimateManHunt</b> plugin main class.
     */
    private final UltimateManHunt plugin;

    /**
     * Reference to the <b>UmhManager</b> instance which manages the plugin's core logic.
     */
    private final UmhManager manager;

    public ToggleDistanceReportingCommandExecutor(UltimateManHunt plugin, UmhManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        manager.setDistanceReportingEnabled(! manager.isDistanceReportingEnabled());

        if(manager.isDistanceReportingEnabled()) {
            Bukkit.broadcastMessage(ChatColor.AQUA + "Distance reporting is enabled");
        }
        else {
            Bukkit.broadcastMessage(ChatColor.AQUA + "Distance reporting is disabled");
        }

        return true;
    }
}
