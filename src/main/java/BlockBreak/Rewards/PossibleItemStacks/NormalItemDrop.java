package BlockBreak.Rewards.PossibleItemStacks;

import BlockBreak.Rewards.RewardsHelper.Chance;
import BlockBreak.GlobalFlags;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class NormalItemDrop {

    public static ItemStack rollNormalItem(BlockClicker plugin, Map<?, ?> rewardData, GlobalFlags flags, Player player, ItemStack toolUsed, String itemName, int amount, double chance) {

        Material rewardItem = Material.matchMaterial(itemName);

        if (rewardItem == null) return null;

        if (Chance.performDropRoll(flags, chance, toolUsed, player)) {
            return new ItemStack(rewardItem, amount);
        }
        return null;
    }
}
