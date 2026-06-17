package BlockBreak.Rewards.PossibleItemStacks;

import BlockBreak.GlobalFlags;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class NormalItemDrop {

    public static ItemStack getNormalItem(String itemName, int amount) {

        Material rewardItem = Material.matchMaterial(itemName);

        if (rewardItem == null) return null;

        return new ItemStack(rewardItem, amount);
    }
}
