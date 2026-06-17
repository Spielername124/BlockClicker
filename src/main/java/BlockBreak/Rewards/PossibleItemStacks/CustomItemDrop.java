package BlockBreak.Rewards.PossibleItemStacks;

import BlockBreak.GlobalFlags;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class CustomItemDrop {
    public static ItemStack getCustomItem(BlockClicker plugin, String itemName, int amount){

        //retrieve saved item
        ItemStack savedItem = plugin.getItemsConfig().getItemStack("saved-items." + itemName);

        if(savedItem == null) return null;

        ItemStack reward = savedItem.clone();

        reward.setAmount(amount);

        return reward;
    }
}
