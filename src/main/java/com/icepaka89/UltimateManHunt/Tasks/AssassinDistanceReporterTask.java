package com.icepaka89.UltimateManHunt.Tasks;

import com.icepaka89.UltimateManHunt.Core.UmhManager;
import com.icepaka89.UltimateManHunt.UltimateManHunt;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * If distance reporting is enabled (see toggle-distance-reporting command), this task will get the distance from
 * every assassin to the nearest speedrunner, and report that distance to both parties.
 *
 * @author : Daniel Gomm
 * @since : 9/2/21, Thu
 */
public class AssassinDistanceReporterTask implements Runnable{

    /**
     * Reference to the <b>UltimateManHunt</b> plugin main class
     */
    private UltimateManHunt plugin;

    /**
     * Reference to the manager class that manages player groups
     */
    private UmhManager manager;

    public AssassinDistanceReporterTask(UltimateManHunt plugin, UmhManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    @Override
    public void run() {
        if(manager.isDistanceReportingEnabled()) {
            for(Player assassin : manager.getAssassins()) {
                // Get the speedrunner closes to the assassin
                Player target = manager.getNearestSpeedRunner(assassin);
                if(target == null) continue;

                // Get the distance between the assassin and nearest speedrunner
                var distance = (int) target.getLocation().distance(assassin.getLocation());

                // Send messages to both assassin and speedrunner reporting the current distance
                target.sendMessage(ChatColor.AQUA
                        + "The nearest assassin, "
                        + ChatColor.RED
                        + assassin.getName()
                        + ChatColor.AQUA
                        + ", is "
                        + distance
                        + " blocks away from you"
                );
                assassin.sendMessage(ChatColor.AQUA
                        + "The nearest speedrunner, "
                        + ChatColor.GREEN
                        + target.getName()
                        + ChatColor.AQUA
                        + ", is "
                        + distance
                        + " blocks away from you"
                );
            }
        }
    }
}
