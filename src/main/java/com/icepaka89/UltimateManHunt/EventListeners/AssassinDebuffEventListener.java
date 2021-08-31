package com.icepaka89.UltimateManHunt.EventListeners;

import com.icepaka89.UltimateManHunt.Core.UmhManager;
import com.icepaka89.UltimateManHunt.UltimateManHunt;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * @author : Daniel Gomm
 * @since : 8/31/21, Tue
 */
public class AssassinDebuffEventListener implements Listener {

    /**
     * Reference to the <b>UltimateManHunt</b> plugin main class.
     */
    private final UltimateManHunt plugin;

    /**
     * Reference to the <b>UmhManager</b> instance which manages the plugin's core logic.
     */
    private final UmhManager manager;

    public AssassinDebuffEventListener(UltimateManHunt plugin, UmhManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player) {
            Player attacker = (Player) event.getDamager();
            if(manager.getAssassins().contains(attacker)) {
                var dmg = event.getDamage();
                var debuffedDmg = dmg * manager.getAssassinDeBuff();
                
                event.setDamage(debuffedDmg);

                Bukkit.broadcastMessage(String.format("Damage reduced from %f to %f!", dmg, debuffedDmg));
            }
        }
    }
}
