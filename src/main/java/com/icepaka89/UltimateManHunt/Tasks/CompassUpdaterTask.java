package com.icepaka89.UltimateManHunt.Tasks;

import com.icepaka89.UltimateManHunt.Core.UmhManager;
import com.icepaka89.UltimateManHunt.UltimateManHunt;
import org.apache.commons.lang.NotImplementedException;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

/**
 * This task handles updating the compass target for each player in the assassins group to point to the
 * location of the closest speed runner relative to each assassin's location. The only limitation is that
 * the speedrunner has to be in the same world as the assassin.
 *
 * @author danielgomm
 * Created: Apr 01 2021 at 11:54 PM
 */
public class CompassUpdaterTask implements Runnable {

    /**
     * Reference to the <b>UltimateManHunt</b> plugin main class
     */
    private UltimateManHunt plugin;

    /**
     * Reference to the manager class that manages player groups
     */
    private UmhManager manager;

    /**
     * This task handles updating the compass target for each player in the assassins group to point to the
     * location of the closest speed runner relative to each assassin's location. The only limitation is that
     * the speedrunner has to be in the same world as the assassin.
     * @param plugin
     * @param manager
     */
    public CompassUpdaterTask(UltimateManHunt plugin, UmhManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    @Override
    public void run() {
        // Go through each player in the speed runners group, and set each assassin's compass target
        // to the location of the closest speed runner.
        for(Player assassin : manager.getAssassins()) {
            Player target = getNearestSpeedRunner(assassin);
            if(target != null) {
                assassin.setCompassTarget(target.getLocation());
            }
        }
    }

    /**
     * Returns the speed runner that's closest to the given assassin within the same world, or null if no
     * such player exists.
     * @param assassin The assassin player to get the closest speed runner for.
     * @return
     */
    private Player getNearestSpeedRunner(Player assassin) {
        Location location = assassin.getLocation();
        Player closestSpeedRunner = manager.getSpeedrunners().stream()
            // Only consider players in the same world as the assassin, and don't consider the assassin
            .filter(speedRunner ->
                    !speedRunner.equals(assassin) && speedRunner.getWorld().equals(assassin.getWorld())
            )
            // Get the player that's closes to the speedrunner's current location
            .min(
                Comparator.comparing(speedRunner -> speedRunner.getLocation().distance(location))
            )
            .orElse(null);

        return closestSpeedRunner;
    }
}
