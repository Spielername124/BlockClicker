package BlockBreak.Rewards.ItemDrop;

import BlockBreak.Rewards.RewardsHelper.Chance;
import BlockBreak.GlobalFlags;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class CustomItemDrop {
    public static ItemStack rollCustomItem(BlockClicker plugin, Map<?, ?> rewardData, GlobalFlags flags, Player player, ItemStack toolUsed, String itemName, int amount, double chance){

        //retrieve saved item
        ItemStack savedItem = plugin.getItemsConfig().getItemStack("saved-items." + itemName);

        if(savedItem == null) return null;

        ItemStack reward = savedItem.clone();

        reward.setAmount(amount);

        if (Chance.performDropRoll(flags, chance, toolUsed, player)) {
            return reward;
        }
        return null;
    }
}
