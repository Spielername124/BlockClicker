package me.Spielername124.blockClicker.RewardManagement.Rewards.GuaranteedReward;

import me.Spielername124.blockClicker.EventWideFlags;
import me.Spielername124.blockClicker.GlobalFlags;
import me.Spielername124.blockClicker.RewardManagement.RewardCreator;
import me.Spielername124.blockClicker.RewardManagement.Rewards.Reward;
import me.Spielername124.blockClicker.RewardManagement.Rewards.RewardSoundAndParticle;
import me.Spielername124.blockClicker.BlockClicker;
import me.Spielername124.blockClicker.RewardManagement.Rewards.RewardsHelper.Amount;
import me.Spielername124.blockClicker.RewardManagement.Rewards.RewardsHelper.WeightedList;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public class GuaranteedReward extends Reward {
    private final int recursionDepth;
    private final WeightedList<Reward> weightedRewardPool = new WeightedList<Reward>();
    private final Amount amount;


    public GuaranteedReward(BlockClicker plugin, FileConfiguration config, Map<?, ?> rewardData, int recursionDepth) {
        super(plugin, config, rewardData);

        this.recursionDepth = recursionDepth;

        //sets a temporary amount for the case that none gets set
        Amount tempAmount = new Amount(1);

        //create the lootPool on creation of this instance
        Object rawReward = rewardData.get("guaranteed-reward");

        if (rawReward instanceof Map<?,?> reward) {

            Object rawAmountMap = reward.get("number_of_rewards");
            if(rawAmountMap instanceof Map<?,?> amountMap) {
                tempAmount  = new Amount(amountMap);
            }

            Object rawList = reward.get("rewards");
            if (rawList instanceof List) {
                for (Object obj : (List<?>) rawList) {
                    if (obj instanceof Map<?, ?> innerData) {

                        //create the rewards
                        Reward compiledReward = RewardCreator.createReward(plugin, config, innerData);

                        if (compiledReward != null) {
                            // Extract and cache the weight
                            Number weightNr = (Number) innerData.get("weight");
                            double weight = weightNr != null ? weightNr.doubleValue() : 1.0;
                            weightedRewardPool.addElement(compiledReward, weight);
                        }
                    }
                }
            }
        }
        //sets the temporary amount as the final amount
        amount = tempAmount;
    }

    @Override
    protected void execute(Player player, Location location, GlobalFlags flags, RewardSoundAndParticle sound, ItemStack toolUsed, Block block, EventWideFlags eventWideFlags){
        List<Reward> rewardList = getChosenReward(flags);
        if(rewardList!=null)
            for (Reward reward : rewardList) {
                reward.rollAndExecute(player, location, sound, toolUsed, block, eventWideFlags);
            }
    }

    public List<Reward> getChosenReward (GlobalFlags flags){
        if (weightedRewardPool.isEmpty())return null;

        if (recursionDepth > flags.recursionDepth) {
            plugin.getLogger().warning("Maximum recursion depth of " + recursionDepth + " reached. Recursion prevention measures were taken by cancelling this reward. If you are certain that no recursion exists, increase the cap");
            return null;
        }
        //return a random reward based on the weight
        return weightedRewardPool.getXRandomElements(amount.getAmount());
    }
}
