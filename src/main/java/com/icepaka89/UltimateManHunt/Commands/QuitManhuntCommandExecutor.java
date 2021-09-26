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
 * @author : Daniel Gomm
 * @since : 9/26/21, Sun
 */
public class QuitManhuntCommandExecutor implements CommandExecutor {

    /**
     * Reference to the <b>UltimateManHunt</b> plugin main class.
     */
    private final UltimateManHunt plugin;

    /**
     * Reference to the <b>UmhManager</b> instance which manages the plugin's core logic.
     */
    private final UmhManager manager;

    public QuitManhuntCommandExecutor(UltimateManHunt plugin, UmhManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        manager.endManhunt();
        manager.clearAssassins();
        manager.clearSpeedRunners();

        if(commandSender instanceof Player) {
            var player = (Player) commandSender;

            Bukkit.broadcastMessage(
                    ChatColor.AQUA
                    + "Ultimate Manhunt has been stopped by "
                    + ChatColor.GREEN
                    + player.getName()
            );
        }

        return true;
    }
}
