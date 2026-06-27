package BlockBreak.RewardManagement.Rewards.PossibleItemStacks;

import BlockBreak.RewardManagement.Rewards.RewardsHelper.Amount;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class NormalItemDrop implements DroppedItem {

    private final ItemStack item;
    private final Amount amount;

    public NormalItemDrop(Map<?, ?> rewardData){
        String itemName = (String) rewardData.get("item");
        Material rewardMaterial = Material.matchMaterial(itemName);
        item = rewardMaterial != null ? new ItemStack(rewardMaterial) : null;
        amount = new Amount(rewardData);
    }


    @Override
    public ItemStack getItem() {
        if(item == null) return null;
        ItemStack reward = item.clone();
        reward.setAmount(amount.getAmount());
        return reward;
    }
}
