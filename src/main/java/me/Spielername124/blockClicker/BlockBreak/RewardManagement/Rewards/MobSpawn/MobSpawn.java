package me.Spielername124.blockClicker.BlockBreak.RewardManagement.Rewards.MobSpawn;

import me.Spielername124.blockClicker.BlockBreak.GlobalFlags;
import me.Spielername124.blockClicker.BlockBreak.RewardManagement.Rewards.Reward;
import me.Spielername124.blockClicker.BlockBreak.RewardManagement.Rewards.RewardSound;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class MobSpawn extends Reward {

    private final EntityType type;

    public MobSpawn(BlockClicker plugin, FileConfiguration config, Map<?, ?> rewardData) {
        super(plugin, config, rewardData);

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
        Entity entity = location.getWorld().spawnEntity(location, type);
    }
}
