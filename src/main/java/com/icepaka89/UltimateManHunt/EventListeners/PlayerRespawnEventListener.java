package com.icepaka89.UltimateManHunt.EventListeners;

import com.icepaka89.UltimateManHunt.Core.UmhItemFactory;
import com.icepaka89.UltimateManHunt.Core.UmhManager;
import com.icepaka89.UltimateManHunt.Enum.ManhuntRole;
import com.icepaka89.UltimateManHunt.UltimateManHunt;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 * @author : Daniel Gomm
 * @since : 9/23/21, Thu
 */
public class PlayerRespawnEventListener implements Listener {

    /**
     * Reference to the <b>UltimateManHunt</b> plugin main class.
     */
    private final UltimateManHunt plugin;

    /**
     * Reference to the <b>UmhManager</b> instance which manages the plugin's core logic.
     */
    private final UmhManager manager;

    public PlayerRespawnEventListener(UltimateManHunt plugin, UmhManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        // Don't listen for player respawns if manhunt isn't currently going on
        if(! manager.isManhuntActive()) return;

        var player = event.getPlayer();

        switch(manager.getPlayerRole(player)) {
            // Every time assassins respawn, give them another compass
            case ASSASSIN: {
                var compass = UmhItemFactory.getAssassinCompass();
                player.getInventory().addItem(compass);
                break;
            }

            // Every time a speed runner dies, give them another freeze sword, if freezing
            // assassins is enabled
            case SPEEDRUNNER: {
                if(manager.isFreezeAssassinEnabled()) {
                    var sword = UmhItemFactory.getSpeedRunnerFreezeSword();
                    player.getInventory().addItem(sword);
                }
                break;
            }
        }
    }
}
