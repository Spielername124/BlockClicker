package me.Spielername124.blockClicker.RewardManagement.Rewards.MobSpawn;

import me.Spielername124.blockClicker.GlobalFlags;
import me.Spielername124.blockClicker.RewardManagement.Rewards.Reward;
import me.Spielername124.blockClicker.RewardManagement.Rewards.RewardSound;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;

public class MobSpawn extends Reward {

    private final EntityType type;
    private final String customMobId;


    public MobSpawn(BlockClicker plugin, FileConfiguration config, Map<?, ?> rewardData) {
        super(plugin, config, rewardData);

        customMobId = (String) rewardData.get("mob-id");

        String mobType = (String) rewardData.get("mob");
        if(mobType==null){
            type = null;
            return;
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

    }

    @Override
    protected void execute(Player player, Location location, GlobalFlags flags, RewardSound sound, ItemStack toolUsed, Block block){
        // make mob spawning mutually exclusive with container spawns
        if(flags.containerHasBeenPlaced)
            return;
        flags.containerHasBeenPlaced = true;

        //center the mob spawn so that mobs don't spawn on the edge of the block
        Location spawnLoc = location.clone();
        spawnLoc.add(0.5, 0.1, 0.5);


        LivingEntity spawnedMob = (LivingEntity) spawnLoc.getWorld().spawnEntity(spawnLoc, type);

        // adds the mobId if one exists
        if (customMobId != null && !customMobId.isEmpty()) {
            spawnedMob.getPersistentDataContainer().set(
                    BlockClicker.MOB_ID_KEY,
                    PersistentDataType.STRING,
                    customMobId
            );
        }

        Location LocationOnZPlus1 = new Location(location.getWorld(),  location.getX(), location.getY()+1, location.getZ());
        if(plugin.zoneCache.isAllowedToBeManipulated(LocationOnZPlus1)){
            Block blockOnZPlus1 = LocationOnZPlus1.getBlock();
            blockOnZPlus1.setType(Material.AIR);
        }

    }
}
