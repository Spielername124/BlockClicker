package BlockBreak.RewardManagement.Rewards.PossibleItemStacks;

import BlockBreak.RewardManagement.Rewards.RewardsHelper.Amount;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class NormalItemDrop extends DroppedItem {


    public NormalItemDrop(Map<?, ?> rewardData){
        super(rewardData);
    }


    @Override
    public ItemStack getItemStack() {
        Material rewardMaterial = Material.matchMaterial(itemType);
        return rewardMaterial != null ? new ItemStack(rewardMaterial) : null;
    }
}
