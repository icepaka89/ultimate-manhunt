package com.icepaka89.UltimateManHunt.Commands;

import com.icepaka89.UltimateManHunt.Core.UmhManager;
import com.icepaka89.UltimateManHunt.UltimateManHunt;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Implementation of the /toggle-freeze-assassin command, toggles the boolean flag in <b>UmhManager</b>
 * that dictates if speedrunners will receive a limited use freeze sword that can freeze an assassin for
 * a short time when used.
 * @author : Daniel Gomm
 * @since : 9/1/21, Wed
 */
public class ToggleFreezeAssassinCommandExecutor implements CommandExecutor {

    /**
     * Reference to the <b>UltimateManHunt</b> plugin main class.
     */
    private final UltimateManHunt plugin;

    /**
     * Reference to the <b>UmhManager</b> instance which manages the plugin's core logic.
     */
    private final UmhManager manager;

    public ToggleFreezeAssassinCommandExecutor(UltimateManHunt plugin, UmhManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        manager.setFreezeAssassinEnabled(! manager.isFreezeAssassinEnabled());

        if(manager.isFreezeAssassinEnabled()) {
            Bukkit.broadcastMessage(ChatColor.AQUA + "Assassin freeze sword for speedrunners is enabled");
        }
        else {
            Bukkit.broadcastMessage(ChatColor.AQUA + "Assassin freeze sword for speedrunners is disabled");
        }

        return true;
    }
}
