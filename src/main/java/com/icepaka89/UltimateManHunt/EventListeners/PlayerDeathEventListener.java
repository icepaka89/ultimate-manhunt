package com.icepaka89.UltimateManHunt.EventListeners;

import com.icepaka89.UltimateManHunt.Core.UmhManager;
import com.icepaka89.UltimateManHunt.Enum.ManhuntRole;
import com.icepaka89.UltimateManHunt.UltimateManHunt;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.time.Duration;
import java.util.Date;

/**
 * Listens for player kill events, and prints the time survived when a speedrunner is killed. If all speedrunners
 * are killed, this listener also ends the manhunt and prints the total run time.
 *
 * @author : Daniel Gomm
 * @since : 9/3/21, Fri
 */
public class PlayerDeathEventListener implements Listener {

    /**
     * Reference to the <b>UltimateManHunt</b> plugin main class.
     */
    private final UltimateManHunt plugin;

    /**
     * Reference to the <b>UmhManager</b> instance which manages the plugin's core logic.
     */
    private final UmhManager manager;

    public PlayerDeathEventListener(UltimateManHunt plugin, UmhManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        // Don't listen for deaths unless a manhunt is currently going on
        if(! manager.isManhuntActive()) return;

        var deadPlayer = event.getEntity();
        var killer = deadPlayer.getKiller();

//        plugin.getLogger().info(deadPlayer.getName() + " was killed by " + killer.getName());

        // If it was an assassin who killed a speed runner, print the time that speed runner stayed alive
        if (
            manager.getPlayerRole(deadPlayer) == ManhuntRole.SPEEDRUNNER
            && manager.getPlayerRole(killer) == ManhuntRole.ASSASSIN
        ) {
            // Print the duration that the speedrunner lasted for!
            var now = new Date().toInstant();
            var start = manager.getManhuntStartTime().toInstant();
            var duration = Duration.between(start, now);

            Bukkit.broadcastMessage(
                    ChatColor.RED
                    + killer.getName()
                    + ChatColor.AQUA
                    + " has killed "
                    + ChatColor.GREEN
                    + deadPlayer.getName()
                    + ChatColor.AQUA
                    + "! They lasted for "
                    + duration.toHoursPart()
                    + "h"
                    + duration.toMinutesPart()
                    + "m"
                    + duration.toSecondsPart()
                    + "s"
            );

            // Remove the dead speed runner from the game, since they've been killed by an assassin
            manager.removeSpeedRunner(deadPlayer);

            // If no speed runners left, then end the manhunt and print  that assassins have won
            if(manager.getSpeedrunners().size() == 0) {
                Bukkit.broadcastMessage(
                        ChatColor.RED
                        + "Assassins"
                        + ChatColor.AQUA
                        + " have won the manhunt!"
                );
                manager.endManhunt();
            }
        }

        // Special message for Jesse if he gets wrecked lmao
        if(deadPlayer.getName().equals("Arksaw")) {
            Bukkit.broadcastMessage(
                    ChatColor.BOLD
                    + "Wow, Jesse you really suck at this game! Maybe Dan Gomm needs to give you some lessons ;)"
            );
        }
    }
}
