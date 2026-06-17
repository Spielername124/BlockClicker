package BlockBreak.Rewards.ContainerSpawn;

import BlockBreak.GlobalFlags;
import BlockBreak.Rewards.PossibleItemStacks.CustomItemDrop;
import BlockBreak.Rewards.PossibleItemStacks.NormalItemDrop;
import BlockBreak.Rewards.RewardsHelper.Amount;
import BlockBreak.Rewards.RewardsHelper.Chance;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class ContainerItems {

    public static ItemStack rollPossibleItem(BlockClicker plugin, Map<?, ?> rewardData, GlobalFlags flags, Player player, ItemStack toolUsed) {

        //get all possibly needed data for a drop
        String itemName = (String) rewardData.get("item");
        boolean isSpecialItem = Boolean.TRUE.equals(rewardData.get("is-custom"));

        Number chanceNr = (Number) rewardData.get("chance");
        double chance = chanceNr != null ? chanceNr.doubleValue() : 100;
        boolean isLuckDependent = Boolean.TRUE.equals(rewardData.get("luck-dependence"));

        if (itemName == null) return null;

        int amount = Amount.getAmount(rewardData);

        //if luck succeeds
        if(!Chance.performDropRoll(flags, chance, toolUsed, player, isLuckDependent)) {
            // returns the possible dropping Stack
            return isSpecialItem ?
                    CustomItemDrop.getCustomItem(plugin, itemName, amount) :
                    NormalItemDrop.getNormalItem( itemName, amount);
        }
        return null;
    }
}
