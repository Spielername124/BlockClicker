package BlockBreak.RewardManagement.Rewards.PossibleItemStacks;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class NormalItemDrop {

    public static ItemStack getNormalItem(String itemName, int amount) {

        Material rewardItem = Material.matchMaterial(itemName);

        if (rewardItem == null) return null;

        return new ItemStack(rewardItem, amount);
    }
}
