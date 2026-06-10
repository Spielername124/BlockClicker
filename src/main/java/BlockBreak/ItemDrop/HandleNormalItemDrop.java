package BlockBreak.ItemDrop;

import BlockBreak.Chance;
import BlockBreak.GlobalFlags;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class HandleNormalItemDrop {

    public static void rollNormalItem(BlockClicker plugin, Map<?, ?> rewardData, GlobalFlags flags, Player player, Location location, ItemStack toolUsed, String brokenBlockName) {
        //get the specifics for possible reward
        String itemName = (String) rewardData.get("item");
        Number amountNr = (Number) rewardData.get("amount");
        Number chanceNr = (Number) rewardData.get("chance");

        if (itemName == null || chanceNr == null) {
            plugin.getLogger().warning("[Config Error] Missing item, or chance for reward in " + brokenBlockName);
            return;
        }

        int amount = 1;
        if (amountNr != null) amount = amountNr.intValue();
        double chance = chanceNr.doubleValue();
        Material rewardItem = Material.matchMaterial(itemName);

        if (rewardItem == null) {
            plugin.getLogger().warning("[Config Error] Invalid material name '" + itemName +
                    "' found under block-rewards." + brokenBlockName);
            return;
        }

        if (Chance.performDropRoll(flags, chance, toolUsed, player)) {
            ItemStack reward = new ItemStack(rewardItem, amount);

            if (flags.depositToInventory) player.getInventory().addItem(reward);
            else location.getWorld().dropItemNaturally(location, reward);
        }
    }
}
