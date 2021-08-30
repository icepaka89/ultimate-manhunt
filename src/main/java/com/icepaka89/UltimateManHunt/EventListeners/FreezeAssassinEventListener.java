package com.icepaka89.UltimateManHunt.EventListeners;

import com.icepaka89.UltimateManHunt.Core.UmhManager;
import com.icepaka89.UltimateManHunt.UltimateManHunt;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * This event listener handles freezing assassins when the countdown timer is running.
 *
 * TODO: Also freeze assassins if they're in view of a speedrunner, and freeze-assassins is enabled.
 *
 * @author Daniel Gomm
 */
public class FreezeAssassinEventListener implements Listener {

    /**
     * Reference to the <b>UltimateManHunt</b> plugin main class.
     */
    private final UltimateManHunt plugin;

    /**
     * Reference to the <b>UmhManager</b> instance which manages the plugin's core logic.
     */
    private final UmhManager manager;

    public FreezeAssassinEventListener(UltimateManHunt plugin, UmhManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        // If player trying to move is in the assassins group when the countdown timer is running, cancel the
        // event to prevent them from moving.
        if (
            manager.isCountdownTimerRunning()
            && manager.getAssassins().stream().anyMatch(p -> p.getName() == player.getName())
        ) {
            plugin.getLogger().info(String.format("%s shouldn't be moving", player.getName()));
            event.setCancelled(true);
        }
    }
}
