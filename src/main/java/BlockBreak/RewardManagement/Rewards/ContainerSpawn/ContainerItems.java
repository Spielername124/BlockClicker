package BlockBreak.RewardManagement.Rewards.ContainerSpawn;

import BlockBreak.GlobalFlags;
import BlockBreak.RewardManagement.Rewards.GuaranteedReward.GuaranteedReward;
import BlockBreak.RewardManagement.Rewards.ItemDrop.ItemDrop;
import BlockBreak.RewardManagement.Rewards.PossibleItemStacks.CustomItemDrop;
import BlockBreak.RewardManagement.Rewards.PossibleItemStacks.NormalItemDrop;
import BlockBreak.RewardManagement.Rewards.Reward;
import BlockBreak.RewardManagement.Rewards.RewardsHelper.Amount;
import BlockBreak.RewardManagement.Rewards.RewardsHelper.Chance;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;


// todo rewrite this to be also precalculated
public class ContainerItems {

    public static ItemStack rollPossibleItem(BlockClicker plugin, Map<?, ?> rewardData, FileConfiguration config, GlobalFlags flags, Player player, ItemStack toolUsed, int recursionDepth) {

        if (recursionDepth > flags.recursionDepth) {
            plugin.getLogger().warning("Maximum recursion depth of " + recursionDepth + " reached. Recursion prevention measures were taken by cancelling this reward. If you are certain that no recursion exists, increase the cap");
            return null;
        }

        //get chance
        Number chanceNr = (Number) rewardData.get("chance");
        double chance = chanceNr != null ? chanceNr.doubleValue() : 100;
        boolean isLuckDependent = Boolean.TRUE.equals(rewardData.get("luck-dependence"));

        //continue if luck succeeds
        if(!Chance.performDropRoll(flags, chance, toolUsed, player, isLuckDependent)) return null;

        //guaranteed drop logic for Items
        if (rewardData.containsKey("guaranteed-reward")) {
            GuaranteedReward guaranteedReward = new GuaranteedReward(plugin, config, rewardData,recursionDepth+1);
            Reward rolledReward =  guaranteedReward.getChosenReward(flags);
            if (rolledReward instanceof ItemDrop){
                rewardData = rolledReward.rewardData;
            }
            if (rolledReward instanceof GuaranteedReward){
                return rollPossibleItem(plugin, rolledReward.rewardData, config, flags, player, toolUsed, recursionDepth+1);
            }

        }

        //regular items logic
        String itemName = (String) rewardData.get("item");
        boolean isSpecialItem = Boolean.TRUE.equals(rewardData.get("is-custom"));
        if (itemName == null) return null;

        int amount = Amount.getAmount(rewardData);

        // returns the possible dropping Stack
        return isSpecialItem ?
                CustomItemDrop.getCustomItem(plugin, itemName, amount) :
                NormalItemDrop.getNormalItem( itemName, amount);

    }
}
