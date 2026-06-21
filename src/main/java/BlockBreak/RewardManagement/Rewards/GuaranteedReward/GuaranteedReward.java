package BlockBreak.RewardManagement.Rewards.GuaranteedReward;

import BlockBreak.GlobalFlags;
import BlockBreak.RewardManagement.Rewards.Reward;
import BlockBreak.RewardManagement.Rewards.RewardSound;
import BlockBreak.RewardManagement.Rewards.RewardsHelper.ExecuteSingleReward;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class GuaranteedReward extends Reward {
    private final int recursionDepth;
    public RewardSound sound = null;
    public GuaranteedReward(BlockClicker plugin, FileConfiguration config, Map<?, ?> rewardData, int recursionDepth) {
        super(plugin, config, rewardData);

        this.recursionDepth = recursionDepth;
    }

    protected void execute (Player player, Location location, GlobalFlags flags, ItemStack toolUsed, Block block){
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
