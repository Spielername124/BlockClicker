package BlockBreak.RewardManagement.Rewards.RewardsHelper;

import BlockBreak.GlobalFlags;
import BlockBreak.RewardManagement.Rewards.MobSpawn.MobSpawn;
import BlockBreak.RewardManagement.Rewards.Reward;
import BlockBreak.RewardManagement.Rewards.RewardSound;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class ExecuteSingleReward {
    public static void executeReward (BlockClicker plugin, FileConfiguration config, RewardSound sound, Map<?, ?> rewardData, GlobalFlags flags, Player player, Block block, Location location, ItemStack toolUsed, int recursionDepth) {

        if (recursionDepth > flags.recursionDepth) {
            plugin.getLogger().warning("Maximum recursion depth of " + recursionDepth + " reached. Recursion prevention measures were taken by cancelling this reward. If you are certain that no recursion exists, increase the cap");
            return;
        }

        if (rewardData.containsKey("item")) {
            //ItemDrop.performItemDrop(plugin, sound, rewardData, flags, player, block, location, toolUsed);
        } else if (rewardData.containsKey("container")) {
            //ContainerDrop.rollContainerDrop(plugin, config, sound, rewardData, flags, player, toolUsed, block);
        } else if (rewardData.containsKey("effect")) {
            //EffectReward.GiveEffect(sound, rewardData, player);
        } else if (rewardData.containsKey("mob")) {
            //Reward a = new MobSpawn(plugin, config, rewardData, sound);
        } else if (rewardData.containsKey("guaranteed-reward")) {
            //GuaranteedReward.performGuaranteedReward(plugin, sound, rewardData, flags, player, block, location, toolUsed, recursionDepth);
        } else if(rewardData.containsKey("command")){
            //CommandExecution.performCommandExecution(plugin, sound, rewardData, player, location);
        }
    }
}
