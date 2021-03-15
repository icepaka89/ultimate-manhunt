package com.icepaka89.UltimateManHunt.Commands;

import com.icepaka89.UltimateManHunt.UltimateManHunt;
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

    private final UltimateManHunt plugin;

    public AssassinCommandExecutor(UltimateManHunt plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("assassin")) {
            if(args.length < 1) return false;

            String playerName = args[0];
            Player player = plugin.getServer().getPlayer(playerName);

            if(player == null) return false;

            plugin.getLogger().info(
                    String.format("%s has been added to assassins", playerName)
            );
            return true;
        }

        return false;
    }
}
