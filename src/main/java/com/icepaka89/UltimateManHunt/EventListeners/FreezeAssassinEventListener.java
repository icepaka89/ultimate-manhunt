package com.icepaka89.UltimateManHunt.EventListeners;

import com.icepaka89.UltimateManHunt.Core.UmhManager;
import com.icepaka89.UltimateManHunt.Enum.ManhuntRole;
import com.icepaka89.UltimateManHunt.UltimateManHunt;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

        // Ensure that the player being considered is an assassin. If not, just return.
        if(manager.getPlayerRole(player) != ManhuntRole.ASSASSIN) return;

        // If assassin tries to move when the countdown timer is running, cancel the event to prevent them
        // from moving.
        if (
            manager.isCountdownTimerRunning()
        ) {
            plugin.getLogger().info(String.format("%s shouldn't be moving", player.getName()));
            event.setCancelled(true);
        }

        // If the assassin is within line of sight of the nearest speedrunner, and freeze assassin is
        // enabled, prevent them from moving.
//        if(
//            manager.checkLineOfSight(player, manager.getNearestSpeedRunner(player))
//        ) {
//            Bukkit.broadcastMessage(ChatColor.AQUA + String.format("%s is frozen!", player.getName()));
//            event.setCancelled(true);
//        }
    }
}
