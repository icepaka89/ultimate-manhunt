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
 * @since : 9/2/21, Thu
 */
public class ResetManhuntGroupsCommandExecutor implements CommandExecutor {
    /**
     * Reference to the <b>UltimateManHunt</b> plugin main class.
     */
    private final UltimateManHunt plugin;

    /**
     * Reference to the <b>UmhManager</b> instance which manages the plugin's core logic.
     */
    private final UmhManager manager;

    public ResetManhuntGroupsCommandExecutor(UltimateManHunt plugin, UmhManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        manager.clearAssassins();
        manager.clearSpeedRunners();

        Bukkit.broadcastMessage(ChatColor.AQUA + "Manhunt groups reset");

        return true;
    }
}
