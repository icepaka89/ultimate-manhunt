package com.icepaka89.UltimateManHunt.Core;

import com.icepaka89.UltimateManHunt.Enum.ManhuntRole;
import com.icepaka89.UltimateManHunt.UltimateManHunt;
import org.apache.commons.lang.NotImplementedException;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;
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
     * Time the current game was started (the game starts when the countdown timer hits 0)
     */
    private Date manhuntStartTime = null;

    /**
     * Time the current manhunt was ended (the game ends when all speedrunners have been killed by an assassin)
     */
    private Date manhuntEndTime = null;

    /**
     * Percentage value [0,1] that assassin's damage is reduced by
     */
    private double assassinDeBuff = 1;

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
     * Boolean flag that's true if the countdown timer is running, false if not.
     */
    private boolean bIsCountdownTimerRunning = false;

    /**
     * Boolean flag that's true if manhunt is currently going on. Manhunt starts when the countdown timer hits 0, and
     * continues to be active until every speedrunner has been killed by an assassin.
     */
    private boolean bIsManhuntActive = false;

    /**
     * Boolean flag that's true if assassin freeze on line of sight is enabled.
     */
    private boolean bFreezeAssassinEnabled = false;

    /**
     * Boolean flag that's true if the plugin show each speedrunner the distance to the nearest assassin, and
     * each assassin the distance to the nearest speedrunner.
     */
    private boolean bDistanceReportingEnabled = true;

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
     * Starts a new manhunt and records the start time
     */
    public void startManhunt() {
        bIsManhuntActive = true;
        manhuntStartTime = new Date();
        manhuntEndTime = null;
    }

    /**
     * Ends the current manhunt and records the end time
     */
    public void endManhunt() {
        bIsManhuntActive = false;
        manhuntEndTime = new Date();
    }

    /**
     * Returns the speed runner that's closest to the given assassin within the same world, or null if no
     * such player exists.
     * @param assassin The assassin player to get the closest speed runner for.
     * @return
     */
    public Player getNearestSpeedRunner(Player assassin) {
        Location location = assassin.getLocation();
        Player closestSpeedRunner = getSpeedrunners().stream()
                // Only consider players in the same world as the assassin, and don't consider the assassin
                .filter(speedRunner ->
                        !speedRunner.equals(assassin) && speedRunner.getWorld().equals(assassin.getWorld())
                )
                // Get the player that's closest to the speedrunner's current location
                .min(
                        Comparator.comparing(speedRunner -> speedRunner.getLocation().distance(location))
                )
                .orElse(null);

        return closestSpeedRunner;
    }

    /**
     * Returns **true** if the assassin is within line of sight of the speedRunner.
     * @param assassin
     * @param speedRunner
     * @return
     */
    public boolean checkLineOfSight(Player assassin, Player speedRunner) {

        throw new NotImplementedException();
    }

    /**
     * Gets the role of the given player, either assassin or speedrunner, or unassigned if they have no role.
     * @param player
     * @return
     */
    public ManhuntRole getPlayerRole(Player player) {
        if(assassins.containsKey(player.getName())) {
            return ManhuntRole.ASSASSIN;
        }

        if(speedRunners.containsKey(player.getName())) {
            return ManhuntRole.SPEEDRUNNER;
        }

        return ManhuntRole.UNASSIGNED;
    }

    public void clearAssassins() {
        this.assassins = new HashMap<>();
    }

    public void clearSpeedRunners() {
        this.speedRunners = new HashMap<>();
    }

    //
    // SETTERS
    //

    /**
     * Enables/disables showing each speedrunner the distance to the nearest assassin, and
     * each assassin the distance to the nearest speedrunner.
     */
    public void setDistanceReportingEnabled(boolean bDistanceReportingEnabled) {
        this.bDistanceReportingEnabled = bDistanceReportingEnabled;
    }

    /**
     * Enables / disables assassin freeze on line of sight
     * @param bFreezeAssassinEnabled
     */
    public void setFreezeAssassinEnabled(boolean bFreezeAssassinEnabled) {
        this.bFreezeAssassinEnabled = bFreezeAssassinEnabled;
    }

    /**
     * Sets a percentage value, capped to the range [0,1], that all assassin player damage will be reduced by.
     * @param assassinDeBuff
     */
    public void setAssassinDeBuff(double assassinDeBuff) {
        double capped = Math.min(assassinDeBuff, 1);

        if(capped < 0) capped = 0;

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
     * Sets countdown timer running flag
     * @param bIsCountdownTimerRunning
     */
    public void setIsCountdownTimerRunning(boolean bIsCountdownTimerRunning) {
        this.bIsCountdownTimerRunning = bIsCountdownTimerRunning;
    }

    //
    // GETTERS
    //

    /**
     * Gets the time the current manhunt was started
     * @return
     */
    public Date getManhuntStartTime() {
        return manhuntStartTime;
    }

    /**
     * Gets the time the current manhunt ended
     * @return
     */
    public Date getManhuntEndTime() {
        return manhuntEndTime;
    }

    /**
     * Returns true if the plugin show each speedrunner the distance to the nearest assassin, and
     * each assassin the distance to the nearest speedrunner.
     */
    public boolean isDistanceReportingEnabled() {
        return bDistanceReportingEnabled;
    }

    /**
     * Returns true if assassin freeze on line of sight is enabled
     * @return
     */
    public boolean isFreezeAssassinEnabled() {
        return bFreezeAssassinEnabled;
    }

    /**
     * Gets the percentage value, capped to the range [0,1], that all assassin player damage will be reduced by.
     */
    public double getAssassinDeBuff() {
        return assassinDeBuff;
    }

    /**
     * Returns true if the countdown timer is running, false if not
     * @return
     */
    public boolean isCountdownTimerRunning() {
        return bIsCountdownTimerRunning;
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
