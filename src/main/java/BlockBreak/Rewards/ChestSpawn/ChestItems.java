package BlockBreak.Rewards.ChestSpawn;

import BlockBreak.GlobalFlags;
import BlockBreak.Rewards.ItemDrop.XpDrop;
import BlockBreak.Rewards.PossibleItemStacks.CustomItemDrop;
import BlockBreak.Rewards.PossibleItemStacks.NormalItemDrop;
import BlockBreak.Rewards.RewardsHelper.Amount;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class ChestItems {

    public static ItemStack rollPossibleItem(BlockClicker plugin, Map<?, ?> rewardData, GlobalFlags flags, Player player, ItemStack toolUsed) {

        //get all possibly needed data for a drop
        String itemName = (String) rewardData.get("item");
        boolean isSpecialItem = Boolean.TRUE.equals(rewardData.get("is-custom"));
        Number chanceNr = (Number) rewardData.get("chance");

        if (itemName == null) return null;


        double chance = chanceNr != null ? chanceNr.doubleValue() : 100;
        int amount = Amount.getAmount(rewardData);


        // returns the possible dropping Stack
        return isSpecialItem ?
                CustomItemDrop.rollCustomItem(plugin, rewardData, flags, player, toolUsed, itemName, amount, chance) :
                NormalItemDrop.rollNormalItem(plugin, rewardData, flags, player, toolUsed, itemName, amount, chance);
    }
}
