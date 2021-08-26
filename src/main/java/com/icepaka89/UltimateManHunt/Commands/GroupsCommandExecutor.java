package com.icepaka89.UltimateManHunt.Commands;

import com.icepaka89.UltimateManHunt.Core.UmhManager;
import com.icepaka89.UltimateManHunt.UltimateManHunt;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.stream.Collectors;

/**
 * Implementation of the /groups command, lists all players in the speedrunner group and all players in the assassins
 * group for everyone to see.
 */
public class GroupsCommandExecutor implements CommandExecutor {

    /**
     * Reference to the <b>UltimateManHunt</b> plugin main class.
     */
    private final UltimateManHunt plugin;

    /**
     * Reference to the <b>UmhManager</b> instance which manages the plugin's core logic.
     */
    private final UmhManager manager;

    public GroupsCommandExecutor(UltimateManHunt plugin, UmhManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        var speedRunners = manager.getSpeedrunners()
                .stream()
                .map(p -> p.getName())
                .collect(Collectors.toList());
        var assassins = manager.getAssassins()
                .stream()
                .map(p -> p.getName())
                .collect(Collectors.toList());

        Bukkit.broadcastMessage("Speedrunners: " + String.join(", ", speedRunners));
        Bukkit.broadcastMessage("Assassins: " + String.join(", ", assassins));

        return true;
    }
}
