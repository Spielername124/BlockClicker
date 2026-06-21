package BlockBreak.RewardManagement.Rewards.MobSpawn;

import BlockBreak.GlobalFlags;
import BlockBreak.RewardManagement.Rewards.Reward;
import BlockBreak.RewardManagement.Rewards.RewardSound;
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
    public MobSpawn(BlockClicker plugin, FileConfiguration config, Map<?, ?> rewardData) {
        super(plugin, config, rewardData);
    }

    @Override
    protected void execute (Player player, Location location, GlobalFlags flags, ItemStack toolUsed, Block block){

        //polls mob type and returns if none is specified
        String mobType = (String) rewardData.get("mob");
        if(mobType==null) return;

        try{
            //get the entity type and spawn the entity
            EntityType type = EntityType.valueOf(mobType);
            Entity entity = location.getWorld().spawnEntity(location, type);

        }
        catch (IllegalArgumentException e) {
            plugin.getLogger().warning("Invalid EntityType '" + mobType);
        }



    }
}
