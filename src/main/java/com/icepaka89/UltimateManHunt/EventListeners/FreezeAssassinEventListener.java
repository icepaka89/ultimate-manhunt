package com.icepaka89.UltimateManHunt.EventListeners;

import com.icepaka89.UltimateManHunt.Core.UmhManager;
import com.icepaka89.UltimateManHunt.Enum.ManhuntRole;
import com.icepaka89.UltimateManHunt.UltimateManHunt;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashMap;

/**
 * This event listener handles freezing assassins when the countdown timer is running, or if they've been
 * hit by a freeze sword
 *
 * TODO: Also freeze assassins if they're in view of a speedrunner, and freeze-assassins is enabled.
 *
 * @author Daniel Gomm
 */
public class FreezeAssassinEventListener implements Listener {

    /**
     * Reference to the <b>UltimateManHunt</b> plugin main class.
     */
    private final UltimateManHunt plugin;

    /**
     * Reference to the <b>UmhManager</b> instance which manages the plugin's core logic.
     */
    private final UmhManager manager;

    /**
     * Key-value list of assassins that are currently frozen. The keys are player names, and the values are
     * booleans that, if true, will prevent the assassin from moving until set back to false. Removing the
     * player name from the map will also free the assassin.
     */
    private HashMap<String, Boolean> frozenAssassins;

    public FreezeAssassinEventListener(UltimateManHunt plugin, UmhManager manager) {
        this.plugin = plugin;
        this.manager = manager;

        frozenAssassins = new HashMap<String, Boolean>();
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if(player == null || player.getName() == null || frozenAssassins == null) return;

        // Ensure that the player being considered is an assassin. If not, just return.
        if(manager.getPlayerRole(player) != ManhuntRole.ASSASSIN) return;

        // If assassin tries to move when the countdown timer is running, cancel the event to prevent them
        // from moving.
        if (
            manager.isCountdownTimerRunning()
            || frozenAssassins.containsKey(player.getName())
        ) {
            plugin.getLogger().info(String.format("%s shouldn't be moving", player.getName()));
            event.setCancelled(true);
        }

        // If the assassin is within line of sight of the nearest speedrunner, and freeze assassin is
        // enabled, prevent them from moving.
//        if(
//            manager.checkLineOfSight(player, manager.getNearestSpeedRunner(player))
//        ) {
//            Bukkit.broadcastMessage(ChatColor.AQUA + String.format("%s is frozen!", player.getName()));
//            event.setCancelled(true);
//        }
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        // Don't run this event handler unless this event is from one player attacking another
        // player. Otherwise, it'll get run for EVERYTHING, including a random zombie attacking
        // a chicken...
        if(! (event.getDamager() instanceof Player && event.getEntity() instanceof Player)) return;

        // Get the damager and damaged entity as Players (above statement verifies this cast is valid)
        Player attacker = (Player) event.getDamager();
        Player victim = (Player) event.getEntity();

        var item = attacker.getInventory().getItemInMainHand();
        ItemStack freezeSword = null;

        if (
            item.getType() == Material.DIAMOND_SWORD
            && item.getItemMeta().getDisplayName().equals("Ultimate Manhunt Freeze Sword")
        ) {
            freezeSword = item;
        }

        if (
            manager.getPlayerRole(attacker) == ManhuntRole.SPEEDRUNNER
            && manager.getPlayerRole(victim) == ManhuntRole.ASSASSIN
            && freezeSword != null
        ) {
            // The freeze sword shouldn't actually do any damage
            event.setDamage(0);

            // Parse the freeze sword's lore to get the number of uses left on it. Unfortunately this is the only
            // way to store the sword's uses as part of its own metadata.
            String usesStr = freezeSword.getItemMeta().getLore().get(0);
            var uses = Integer.parseInt(usesStr.split(": ")[1]);
            uses--;

            // Update the freeze sword's lore so the number of uses is updated when viewed
            var meta = freezeSword.getItemMeta();
            meta.setLore(Arrays.asList(String.format("Uses: %d", uses), "Freezes assassin for 15 seconds when used!"));
            freezeSword.setItemMeta(meta);

            // Remove the freeze sword from inventory once all its uses have been expended
            if(uses == 0) {
                attacker.getInventory().remove(freezeSword);
            }

            // Freeze the assassin that was hit
            frozenAssassins.put(victim.getName(), true);
            Bukkit.broadcastMessage(ChatColor.RED + victim.getName() + ChatColor.AQUA + " is frozen for 15 seconds!");

            // Unfreeze the assassin after 15 seconds
            var unFreezeTask = new BukkitRunnable() {
                @Override
                public void run() {
                    frozenAssassins.put(victim.getName(), false);
                    Bukkit.broadcastMessage(ChatColor.RED + victim.getName() + ChatColor.AQUA + " is unfrozen!");
                }
            };
            unFreezeTask.runTaskLater(plugin, 15 * 20L);
        }
    }
}
