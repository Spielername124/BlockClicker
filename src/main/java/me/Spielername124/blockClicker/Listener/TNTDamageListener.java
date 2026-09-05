package me.Spielername124.blockClicker.Listener;

import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.persistence.PersistentDataType;

public class TNTDamageListener implements Listener {

    @EventHandler
    public void onTNTDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof TNTPrimed tnt) {

            var pdc = tnt.getPersistentDataContainer();
            if (pdc.has(BlockClicker.TNT_DAMAGE_KEY, PersistentDataType.DOUBLE)) {
                Double modifier = pdc.get(BlockClicker.TNT_DAMAGE_KEY, PersistentDataType.DOUBLE);

                if (modifier != null) {
                    event.setDamage(event.getDamage() * modifier);
                }
            }
        }
    }
}