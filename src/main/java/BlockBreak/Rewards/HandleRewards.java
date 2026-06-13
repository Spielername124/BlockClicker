package BlockBreak.Rewards;

import BlockBreak.GlobalFlags;
import BlockBreak.Rewards.ChestSpawn.ChestDrop;
import BlockBreak.Rewards.ItemDrop.ItemDrop;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import java.util.Map;

public class HandleRewards {
    public static void handleGroupDrops(BlockClicker plugin, FileConfiguration config, GlobalFlags flags, Player player, Block block, Location location, ItemStack toolUsed, String parentGroup){
        String brokenBlockName = block.getType().name();
        String path = "block-rewards." + parentGroup + "." + brokenBlockName;

        //returns if the block has no specified rewards
        if (!config.contains(path)) {
            return;
        }

        List<Map<?, ?>> possibleRewards = config.getMapList(path);

        // Iterating through every possible reward for the broken block
        for(Map<?, ?> rewardData : possibleRewards){

            if(rewardData.containsKey("item")){
                ItemDrop.performItemDrop(plugin, rewardData, flags, player, block, location, toolUsed);
                continue;
            }

            if(rewardData.containsKey("chest")){
                ChestDrop.rollChestDrop(plugin, config, rewardData, flags, player, toolUsed, location);
            }

        }
    }
}
