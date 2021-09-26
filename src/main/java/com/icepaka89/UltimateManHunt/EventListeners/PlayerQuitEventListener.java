package com.icepaka89.UltimateManHunt.EventListeners;

import com.icepaka89.UltimateManHunt.Core.UmhManager;
import com.icepaka89.UltimateManHunt.UltimateManHunt;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Removes players from Ultimate Manhunt when they quit
 *
 * @author : Daniel Gomm
 * @since : 9/26/21, Sun
 */
public class PlayerQuitEventListener implements Listener {

    /**
     * Reference to the <b>UltimateManHunt</b> plugin main class.
     */
    private final UltimateManHunt plugin;

    /**
     * Reference to the <b>UmhManager</b> instance which manages the plugin's core logic.
     */
    private final UmhManager manager;

    public PlayerQuitEventListener(UltimateManHunt plugin, UmhManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        var player = event.getPlayer();

        switch(manager.getPlayerRole(player)) {
            case ASSASSIN: {
                manager.removeAssassin(player);
                break;
            }
            case SPEEDRUNNER: {
                manager.removeSpeedRunner(player);
                break;
            }
        }

        // If either group becomes empty, then the game ends and nobody wins
        if(manager.getSpeedrunners().size() == 0 || manager.getAssassins().size() == 0) {
            Bukkit.broadcastMessage(
                    ChatColor.AQUA
                    + "Not enough players left to continue Ultimate Manhunt. The game has ended."
            );
            manager.endManhunt();
        }
    }
}
