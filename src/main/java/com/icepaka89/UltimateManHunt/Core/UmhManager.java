package com.icepaka89.UltimateManHunt.Core;

import com.icepaka89.UltimateManHunt.UltimateManHunt;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
     * Starting distance between the assassin and speed runner groups when the game is started, in blocks
     */
    private int startingDistance = 20;

    /**
     * Time (in sec) the countdown timer should wait for when the game starts, before the assassin should start
     * going after the speedrunner
     */
    private int countdownTime = 60;

    /**
     * Creates a new Ultimate Manhunt plugin manager
     * @param plugin Handle to the UltimateManHunt plugin main class
     */
    public UmhManager(UltimateManHunt plugin) {
        this.plugin = plugin;

        this.assassins = new HashMap<>();
        this.speedRunners = new HashMap<>();
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
     * Sets the distance (in blocks) that the assassins group starts from the speed runners group when the game starts.
     * @param startingDistance
     */
    public void setStartingDistance(int startingDistance) {
        this.startingDistance = startingDistance;
    }

    /**
     * Sets the time (in sec) the countdown timer should wait for when the game starts, before the assassin should
     * start going after the speedrunner
     * @return
     */
    public void setCountdownTime(int countdownTime) {
        this.countdownTime = countdownTime;
    }

    /**
     * Gets the time (in sec) the countdown timer should wait for when the game starts, before the assassin should
     * start going after the speedrunner
     * @return
     */
    public int getCountdownTime() {
        return countdownTime;
    }

    /**
     * Gets the distance (in blocks) that the assassins group starts from the speed runners group when the game starts.
     * @return
     */
    public int getStartingDistance() { return startingDistance; }

    /**
     * Gets a list of all players in the assassins group
     * @return
     */
    public Collection<Player> getAssassins() {
        return assassins.values();
    }

    /**
     * Gets a list of all players in the speedrunners group
     * @return
     */
    public Collection<Player> getSpeedrunners() {
        return speedRunners.values();
    }

    /**
     * Gets a list of all players, from both assassins and speedrunner groups combined
     * @return
     */
    public Collection<Player> getAllPlayers() {
        var list = Stream.concat(assassins.values().stream(), speedRunners.values().stream());
        return list.collect(Collectors.toList());
    }
}
