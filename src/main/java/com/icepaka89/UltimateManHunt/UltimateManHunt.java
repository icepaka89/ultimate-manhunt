package com.icepaka89.UltimateManHunt;

import com.icepaka89.UltimateManHunt.Commands.*;
import com.icepaka89.UltimateManHunt.Core.UmhManager;
import com.icepaka89.UltimateManHunt.EventListeners.AssassinDebuffEventListener;
import com.icepaka89.UltimateManHunt.EventListeners.FreezeAssassinEventListener;
import com.icepaka89.UltimateManHunt.Tasks.AssassinDistanceReporterTask;
import com.icepaka89.UltimateManHunt.Tasks.CompassUpdaterTask;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Ultimate Man Hunt minecraft bukkit plugin. Little side note: To download javadoc documentation for the
 * bukkit apis, right click on the project, then go to maven->Download Documentation, and then you're
 * good to go.
 *
 * @author icepaka89
 * 2021-03-14
 */
public final class UltimateManHunt extends JavaPlugin {

    /**
     * Run when the plugin is enabled. By default, the plugin will automatically enable itself when
     * loaded
     */
    @Override
    public void onEnable() {
        UmhManager umhManager = new UmhManager(this);

        getLogger().info("UltimateManHunt plugin enabled!");

        //
        // COMMAND EXECUTORS
        //

        getCommand("assassin").setExecutor(
                new AssassinCommandExecutor(this, umhManager)
        );
        getCommand("speedrunner").setExecutor(
                new SpeedrunnerCommandExecutor(this, umhManager)
        );
        getCommand("randomize-spawn").setExecutor(
                new RandomizeSpawnCommandExecutor(this, umhManager)
        );
        getCommand("manhunt-groups").setExecutor(
                new ManhuntGroupsCommandExecutor(this, umhManager)
        );
        getCommand("reset-manhunt-groups").setExecutor(
                new ResetManhuntGroupsCommandExecutor(this, umhManager)
        );
        getCommand("starting-distance").setExecutor(
                new StartingDistanceCommandExecutor(this, umhManager)
        );
        getCommand("start-manhunt").setExecutor(
                new StartManhuntCommandExecutor(this, umhManager)
        );
        getCommand("countdown-time").setExecutor(
                new CountdownTimeCommandExecutor(this, umhManager)
        );
        getCommand("debuff-assassin").setExecutor(
                new DebuffAssassinCommandExecutor(this, umhManager)
        );
        getCommand("toggle-freeze-assassin").setExecutor(
                new ToggleFreezeAssassinCommandExecutor(this, umhManager)
        );

        //
        // TASKS
        //

        getServer().getScheduler().scheduleSyncRepeatingTask(
                this,
                new CompassUpdaterTask(this, umhManager),
                0,
                1
        );

        getServer().getScheduler().scheduleSyncRepeatingTask(
                this,
                new AssassinDistanceReporterTask(this, umhManager),
                0,
                30 * 20L
        );

        //
        // EVENT LISTENERS
        //

        getServer().getPluginManager().registerEvents(
                new FreezeAssassinEventListener(this, umhManager),
                this
        );

        getServer().getPluginManager().registerEvents(
                new AssassinDebuffEventListener(this, umhManager),
                this
        );
    }

    /**
     * Run when the plugin is disabled.
     */
    @Override
    public void onDisable() {
        getLogger().info("UltimateManHunt plugin disabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }
}
