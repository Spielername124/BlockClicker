package me.Spielername124.blockClicker.RewardManagement.Rewards.MobSpawn.Mobs;

import me.Spielername124.blockClicker.BlockClicker;
import me.Spielername124.blockClicker.EventWideFlags;
import me.Spielername124.blockClicker.GlobalFlags;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.util.Map;

public class CommonMob extends SpawnableMob{

    public CommonMob(BlockClicker plugin, Map<?, ?> rewardData, EntityType type) {
        super(plugin, rewardData, type);
    }

    @Override
    public LivingEntity spawn(BlockClicker plugin, GlobalFlags flags, Location location, EventWideFlags eventWideFlags){
        return super.spawn(plugin, flags, location, eventWideFlags);
    }
}
