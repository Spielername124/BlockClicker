package BlockBreak.RewardManagement.Rewards.PossibleItemStacks;

import BlockBreak.RewardManagement.Rewards.RewardsHelper.Amount;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class CustomItemDrop implements DroppedItem {

    private final ItemStack savedItem;
    private final Amount amount;

    public CustomItemDrop(BlockClicker plugin, Map<?, ?> rewardData){

        String itemName = (String) rewardData.get("item");
        //retrieve saved item
        savedItem = plugin.getItemsConfig().getItemStack("saved-items." + itemName);

        amount = new Amount(rewardData);

    }

    @Override
    public ItemStack getItem() {
        if(savedItem == null) return null;
        ItemStack reward = savedItem.clone();
        reward.setAmount(amount.getAmount());
        return reward;
    }
}

