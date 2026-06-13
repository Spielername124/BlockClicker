package BlockBreak.Rewards.ItemDrop;

import BlockBreak.GlobalFlags;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class ItemDrops {
    public static void performItemDrop(BlockClicker plugin, Map<?, ?> rewardData, GlobalFlags flags, Player player, Block block, Location location, ItemStack toolUsed){

        String brokenBlockName = block.getType().name();

        //get all possibly needed data for a drop
        String itemName = (String) rewardData.get("item");
        boolean isSpecialItem = Boolean.TRUE.equals(rewardData.get("is-custom"));
        Number amountNr = (Number) rewardData.get("amount");
        Number chanceNr = (Number) rewardData.get("chance");

        if (itemName == null) {
            plugin.getLogger().warning("[Config Error] Missing item in " + brokenBlockName);
            return;
        }

        //sets amount and chance to the set values/ to the default, if not specified in the config
        int amount = amountNr != null ? amountNr.intValue() : 1;
        double chance = chanceNr != null ? chanceNr.doubleValue() : 100;

        // get the possible droping Stack
        ItemStack reward = isSpecialItem ?
                CustomItemDrop.rollCustomItem(plugin, rewardData, flags, player, toolUsed, itemName, amount, chance):
                NormalItemDrop.rollNormalItem(plugin, rewardData, flags, player, toolUsed, brokenBlockName, itemName, amount, chance);

        if (reward != null){
            if (flags.depositToInventory) player.getInventory().addItem(reward);
            else location.getWorld().dropItemNaturally(location, reward);
        }
    }
}
