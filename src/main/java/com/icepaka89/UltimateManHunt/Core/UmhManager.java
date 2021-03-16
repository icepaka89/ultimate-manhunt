package com.icepaka89.UltimateManHunt.Core;

import com.icepaka89.UltimateManHunt.UltimateManHunt;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Manages all core logic for the ultimate manhunt plugin. This includes managing lists of all
 * players in each role (and their plugin-related metadata), storing player-configurable settings
 * for the plugin,
 * @author icepaka89
 */
public class UmhManager {

    /**
     * Handle to the <b>UltimateManHunt</b> plugin main class.
     */
    private final UltimateManHunt plugin;

    /**
     * Players in the assassins group, keyed by their username
     */
    private HashMap<String, Player> assassins;

    /**
     * Players in the speedrunners group, keyed by their username
     */
    private HashMap<String, Player> speedRunners;

    /**
     * Percentage value [0,1] that assassin's damage is reduced by
     */
    private double assassinDeBuff;

    /**
     * Starting distance between the assassin and speed runner groups when the game is started.
     */
    private double startingDistance;

    /**
     * Creates a new Ultimate Manhunt plugin manager
     * @param plugin Handle to the UltimateManHunt plugin main class
     */
    public UmhManager(UltimateManHunt plugin) {
        this.plugin = plugin;
    }

    /**
     * Adds the player to the assassins group.
     * @param p
     */
    public void addAssassin(Player p) {
        assassins.put(p.getName(), p);
    }

    /**
     * Adds the player to the speed runners group.
     * @param p
     */
    public void addSpeedRunner(Player p) {
        speedRunners.put(p.getName(), p);
    }

    /**
     * Sets a percentage value, capped to the range [0,1], that all assassin player damage will be reduced by.
     * @param assassinDeBuff
     */
    public void setAssassinDeBuff(double assassinDeBuff) {
        double capped = Math.min(assassinDeBuff, 0);
        capped = Math.max(assassinDeBuff, 1);

        this.assassinDeBuff = capped;
    }

    /**
     * Sets the distance that the assassins group starts from the speed runners group when the game starts.
     * @param startingDistance
     */
    public void setStartingDistance(double startingDistance) {
        this.startingDistance = startingDistance;
    }
}
