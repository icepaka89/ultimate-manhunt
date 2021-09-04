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
import java.time.Instant;
import java.util.Date;

/**
 * Listens for player kill events, and prints the time survived when a speedrunner is killed. If all speedrunners
 * are killed, this listener also ends the manhunt and prints the total run time.
 *
 * @author : Daniel Gomm
 * @since : 9/3/21, Fri
 */
public class PlayerKillEventListener implements Listener {

    /**
     * Reference to the <b>UltimateManHunt</b> plugin main class.
     */
    private final UltimateManHunt plugin;

    /**
     * Reference to the <b>UmhManager</b> instance which manages the plugin's core logic.
     */
    private final UmhManager manager;

    public PlayerKillEventListener(UltimateManHunt plugin, UmhManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    @EventHandler
    public void onPlayerKill(PlayerDeathEvent event) {
        // Don't listen for deaths unless a manhunt is currently going on
        if(! manager.isManhuntActive()) return;

        var speedRunner = event.getEntity();
        var assassin = speedRunner.getKiller();

        plugin.getLogger().info(speedRunner.getName() + " was killed by " + assassin.getName());
        if (
            manager.getPlayerRole(speedRunner) == ManhuntRole.SPEEDRUNNER
            && manager.getPlayerRole(assassin) == ManhuntRole.ASSASSIN
        ) {
            var now = new Date().toInstant();
            var start = manager.getManhuntStartTime().toInstant();
            var duration = Duration.between(start, now);

            Bukkit.broadcastMessage(
                    ChatColor.RED
                    + assassin.getName()
                    + ChatColor.AQUA
                    + " has killed "
                    + ChatColor.GREEN
                    + speedRunner.getName()
                    + ChatColor.AQUA
                    + "! They lasted for "
                    + duration.toHoursPart()
                    + "h"
                    + duration.toMinutesPart()
                    + "m"
                    + duration.toSecondsPart()
                    + "s"
            );
        }
    }
}
