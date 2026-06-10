package BlockBreak.ItemDrop;

import BlockBreak.Chance;
import BlockBreak.GlobalFlags;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class HandleCustomItemDrop {
    public static void rollCustomItem(BlockClicker plugin, Map<?, ?> rewardData, GlobalFlags flags, Player player, Location location, ItemStack toolUsed){
        String customId = (String) rewardData.get("custom-item");

        //retrive saved item
        ItemStack savedItem = plugin.getItemsConfig().getItemStack("saved-items." + customId);

        if(savedItem == null) return;

        ItemStack reward = savedItem.clone();

        if (rewardData.containsKey("amount")) {
            reward.setAmount(((Number) rewardData.get("amount")).intValue());
        }
        double chance = ((Number) rewardData.get("chance")).intValue();

        if (Chance.performDropRoll(flags, chance, toolUsed, player)) {
            if (flags.depositToInventory) {
                player.getInventory().addItem(reward);
            } else {
                location.getWorld().dropItemNaturally(location, reward);
            }
        }


    }
}
