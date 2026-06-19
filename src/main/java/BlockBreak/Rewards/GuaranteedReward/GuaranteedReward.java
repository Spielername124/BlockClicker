package BlockBreak.Rewards.GuaranteedReward;

import BlockBreak.GlobalFlags;
import BlockBreak.Rewards.RewardSound;
import BlockBreak.Rewards.RewardsHelper.ExecuteSingleReward;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class GuaranteedReward {

    public static void performGuaranteedReward(BlockClicker plugin, RewardSound sound, Map<?, ?> rewardData, GlobalFlags flags, Player player, Block block, Location location, ItemStack toolUsed, int recursionDepth){
        //sets the sound if one exists
        sound.setSound(rewardData);

        Object rawList = rewardData.get("guaranteed-reward");

        if (rawList instanceof List) {

            List<?> innerRewardsList = (List<?>) rawList;
            Map<?, ?> innerRewardData = getRandomWeightedReward(innerRewardsList);
            ExecuteSingleReward.executeReward(plugin, plugin.getConfig(), sound, innerRewardData, flags, player, block, location, toolUsed, recursionDepth + 1);
        }

    }

    // gets the reward randomly by the weighted list
    public static Map<?, ?> getRandomWeightedReward(List<?> rewardsList) {
        double totalWeight = 0.0;
        for (Object obj : rewardsList) {
            if (obj instanceof Map<?, ?> rewardData) {
                Number weightNr = (Number) rewardData.get("weight");
                totalWeight += weightNr!= null ? weightNr.doubleValue() : 1.0;
            }
        }
        double rolledWeight = ThreadLocalRandom.current().nextDouble(totalWeight);

        Map<?, ? > rewardData = null;
        double currentWeight = 0;

        for (Object obj : rewardsList) {
            if (obj instanceof Map<?, ?>) {
                rewardData = (Map<?, ?>) obj;
                Number weightNr = (Number) rewardData.get("weight");
                currentWeight += weightNr != null ? weightNr.doubleValue() : 1.0;

                if (currentWeight >= rolledWeight) {
                    break;
                }
            }
        }


        return rewardData;
    }

}
