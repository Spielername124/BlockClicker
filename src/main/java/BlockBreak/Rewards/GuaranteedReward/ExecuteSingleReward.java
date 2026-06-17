package BlockBreak.Rewards.GuaranteedReward;

import BlockBreak.GlobalFlags;
import BlockBreak.Rewards.ContainerSpawn.ContainerDrop;
import BlockBreak.Rewards.Effects.Effects;
import BlockBreak.Rewards.ItemDrop.ItemDrop;
import BlockBreak.Rewards.MobSpawn.MobSpawn;
import BlockBreak.Rewards.RewardSound;
import BlockBreak.Rewards.RewardsHelper.Chance;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class ExecuteSingleReward {
    public static void executeReward (BlockClicker plugin, FileConfiguration config, RewardSound sound, Map<?, ?> rewardData, GlobalFlags flags, Player player, Block block, Location location, ItemStack toolUsed){

        if(rewardData.containsKey("item")){
            ItemDrop.performItemDrop(plugin, sound, rewardData, flags, player, block, location, toolUsed);
        }

        if(rewardData.containsKey("container")){
            ContainerDrop.rollContainerDrop(plugin, config, sound, rewardData, flags, player, toolUsed, block);
        }

        if(rewardData.containsKey("effect")){
            Effects.GiveEffect(sound, rewardData, player);
        }
        if(rewardData.containsKey("mob")){
            MobSpawn.rollMobSpawn(plugin, sound, rewardData, flags, player, location ,toolUsed);
        }
    }
}
