package BlockBreak.Rewards;

import BlockBreak.GlobalFlags;
import BlockBreak.Rewards.RewardsHelper.Chance;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GuaranteedReward {
    public void rollGuaranteedReward(BlockClicker plugin, RewardSound sound, Map<?, ?> rewardData, GlobalFlags flags, Player player, Block block, Location location, ItemStack toolUsed){
        //get the chance to be rewarded
        Number chanceNr = (Number) rewardData.get("chance");
        double chance = chanceNr != null ? chanceNr.doubleValue() : 100;
        boolean isLuckDependent = Boolean.TRUE.equals(rewardData.get("luck-dependence"));
        //reward if the chance is met.
        if(Chance.performDropRoll(flags, chance, toolUsed, player, isLuckDependent)){
            performGuaranteedReward(plugin, sound, rewardData, flags, player, block, location, toolUsed);
        }
    }

    private void performGuaranteedReward(BlockClicker plugin, RewardSound sound, Map<?, ?> rewardData, GlobalFlags flags, Player player, Block block, Location location, ItemStack toolUsed){

        //get the list of possible Rewards and check if it has entries
        List<?> innerRewardsList = Collections.singletonList(rewardData.get("guaranteedReward"));
        if (innerRewardsList == null || innerRewardsList.isEmpty()) {
            return;
        }

    }

    private Map<?, ?> getRandomWeightedReward(List<?> rewardsList) {
        double totalWeight = 0.0;
        for (Object obj : rewardsList) {
            if (obj instanceof Map<?, ?> RewardData) {
                Number weightNr = (Number) RewardData.get("weight");
                totalWeight += weightNr!= null ? weightNr.doubleValue() : 1.0;
            }
        }
        return null;
    }

}
