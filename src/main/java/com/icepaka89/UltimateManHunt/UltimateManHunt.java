package com.icepaka89.UltimateManHunt;

import com.icepaka89.UltimateManHunt.Commands.AssassinCommandExecutor;
import com.icepaka89.UltimateManHunt.Commands.RandomizeSpawnCommandExecutor;
import com.icepaka89.UltimateManHunt.Core.UmhManager;
import com.icepaka89.UltimateManHunt.Tasks.CompassUpdaterTask;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collection;

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
        getCommand("assassin").setExecutor(
            new AssassinCommandExecutor(this, umhManager)
        );
        getCommand("randomize-spawn").setExecutor(
            new RandomizeSpawnCommandExecutor(this, umhManager)
        );
        getServer().getScheduler().scheduleSyncRepeatingTask(
            this,
            new CompassUpdaterTask(this, umhManager),
            0,
            1
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
