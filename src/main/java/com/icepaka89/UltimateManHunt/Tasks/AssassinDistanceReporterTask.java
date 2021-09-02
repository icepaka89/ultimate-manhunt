package com.icepaka89.UltimateManHunt.Tasks;

import com.icepaka89.UltimateManHunt.Core.UmhManager;
import com.icepaka89.UltimateManHunt.UltimateManHunt;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
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

        for(Player assassin : manager.getAssassins()) {
            Player target = manager.getNearestSpeedRunner(assassin);
            if(target == null) continue;
            var distance = (int) target.getLocation().distance(assassin.getLocation());
            target.sendMessage(ChatColor.AQUA + "The nearest assassin, " + ChatColor.RED + assassin.getName() + ChatColor.AQUA + ", is " + distance + " blocks away from you");
        }
    }
}
