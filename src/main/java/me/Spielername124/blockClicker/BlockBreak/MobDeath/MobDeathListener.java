package me.Spielername124.blockClicker.BlockBreak.MobDeath;

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
    private final RewardCache rewardCache;
    private final ToolCache toolCache;
    private final ZoneCache zoneCache;


    public MobDeathListener (BlockClicker plugin, RewardCache rewardCache, ToolCache toolCache, ZoneCache zoneCache){
        this.plugin = plugin;
        this.rewardCache = rewardCache;
        this.toolCache = toolCache;
        this.zoneCache = zoneCache;
    }


    @EventHandler
    public void onMobDeath(EntityDeathEvent event){
        LivingEntity entity = event.getEntity();
        //return if this was not a BlockClicker mob
        if (!entity.getPersistentDataContainer().has(MOB_ID_KEY, PersistentDataType.STRING)) return;

        MobDeathHandler.handleMobDeath(plugin, event);

    }
}
