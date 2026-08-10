package me.Spielername124.blockClicker.RewardManagement.Rewards.MobSpawn.Mobs;

import me.Spielername124.blockClicker.BlockClicker;
import me.Spielername124.blockClicker.GlobalFlags;
import me.Spielername124.blockClicker.RewardManagement.Rewards.MobSpawn.MobSpawn;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;

import java.util.Map;

public class MobCreator {

    public static SpawnableMob createMob(BlockClicker plugin, GlobalFlags flags, Map<?, ?> rewardData){
        EntityType type= null;

        String mobType = (String) rewardData.get("mob");
        if(mobType==null){
            type = null;
            return null;
        }
        EntityType localType = null;
        try{
            //get the entity type
            localType = EntityType.valueOf(mobType);
        }
        catch (IllegalArgumentException e) {
            plugin.getLogger().warning("Invalid EntityType '" + mobType);
        }
        type = localType;


        return (type == EntityType.VILLAGER || type == EntityType.WANDERING_TRADER)?
                new Trader(plugin,flags, rewardData, type):
                new CommonMob(plugin, rewardData, type);
    }
}
