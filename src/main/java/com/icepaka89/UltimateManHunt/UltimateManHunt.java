package com.icepaka89.UltimateManHunt;

import com.icepaka89.UltimateManHunt.Commands.AssassinCommandExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Ultimate Man Hunt minecraft bukkit plugin. Little side note: To download javadoc documentation for the
 * bukkit apis, right click on the project, then go to maven->Download Documentation, and then your'e
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
        getLogger().info("UltimateManHunt plugin enabled!");
        getCommand("assassin").setExecutor(new AssassinCommandExecutor(this));
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
