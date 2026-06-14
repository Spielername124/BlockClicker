package BlockBreak.Rewards;

import BlockBreak.GlobalFlags;
import BlockBreak.Rewards.ChestSpawn.ContainerDrop;
import BlockBreak.Rewards.Effects.Effects;
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
    public static void handleGroupDrops(BlockClicker plugin, FileConfiguration config, GlobalFlags flags, Player player, Block block, Location location, ItemStack toolUsed, String parentGroup, String zoneGroup){
        String brokenBlockName = block.getType().name();
        String path = zoneGroup+ "." + parentGroup + "." + brokenBlockName;

        //returns if the block has no specified rewards
        if (!config.contains(path)) {
            return;
        }

        //creates a rewardSound to play a sound in case of a reward
        RewardSound sound = new RewardSound(plugin);

        List<Map<?, ?>> possibleRewards = config.getMapList(path);

        // Iterating through every possible reward for the broken block
        for(Map<?, ?> rewardData : possibleRewards){

            if(rewardData.containsKey("item")){
                ItemDrop.performItemDrop(plugin, sound, rewardData, flags, player, block, location, toolUsed);
                continue;
            }

            if(rewardData.containsKey("container")){
                ContainerDrop.rollContainerDrop(plugin, config, sound, rewardData, flags, player, toolUsed, block);
                continue;
            }

            if(rewardData.containsKey("effect")){
                Effects.GiveEffect(sound, rewardData, flags, player, toolUsed);
            }

        }
        sound.PlaySound(flags, player, location);
    }
}
