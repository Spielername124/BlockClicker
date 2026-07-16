package me.Spielername124.blockClicker.BlockBreak.MobDeath;

import me.Spielername124.blockClicker.BlockBreak.GlobalFlags;
import me.Spielername124.blockClicker.BlockBreak.RewardManagement.RewardCache;
import me.Spielername124.blockClicker.BlockBreak.ToolManagement.ToolCache;
import me.Spielername124.blockClicker.BlockBreak.ZoneManagement.ZoneCache;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.persistence.PersistentDataType;

import static me.Spielername124.blockClicker.BlockClicker.MOB_ID_KEY;


public class MobDeathListener implements Listener {
    private final BlockClicker plugin;
    private final MobLootCache mobLootCache;


    public MobDeathListener (BlockClicker plugin, MobLootCache mobLootCache){
        this.plugin = plugin;
        this.mobLootCache = mobLootCache;

    }


    @EventHandler(ignoreCancelled = true)
    public void onMobDeath(EntityDeathEvent event){
        LivingEntity entity = event.getEntity();
        //return if this was not a BlockClicker mob
        if (!entity.getPersistentDataContainer().has(MOB_ID_KEY, PersistentDataType.STRING)) return;
        //makes a local copy of the global flags
        GlobalFlags flags = new GlobalFlags(plugin.flags);

        MobDeathHandler.handleMobDeath(plugin, flags, event, mobLootCache);

    }
}
