package com.icepaka89.UltimateManHunt.Core;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * Generates all ultimate-manhunt-specific items
 *
 * @author : Daniel Gomm
 * @since : 9/23/21, Thu
 */
public class UmhItemFactory {
    public static ItemStack getSpeedRunnerFreezeSword() {
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        var swordMeta = sword.getItemMeta();
        swordMeta.setDisplayName("Ultimate Manhunt Freeze Sword");
        swordMeta.setLore(Arrays.asList("Uses: 2", "Freezes assassin for 15 seconds when used!"));
        sword.setItemMeta(swordMeta);

        return sword;
    }

    public static ItemStack getAssassinCompass() {
        ItemStack compass = new ItemStack(Material.COMPASS);
        var compassMeta = compass.getItemMeta();
        compassMeta.setDisplayName("Ultimate Manhunt Compass");
        compassMeta.setLore(Arrays.asList("Points you in the direction of the nearest speed runner"));
        compass.setItemMeta(compassMeta);

        return compass;
    }
}
