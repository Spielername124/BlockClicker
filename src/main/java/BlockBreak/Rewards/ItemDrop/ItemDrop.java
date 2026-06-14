package BlockBreak.Rewards.ItemDrop;

import BlockBreak.GlobalFlags;
import BlockBreak.Rewards.PossibleItemStacks.CustomItemDrop;
import BlockBreak.Rewards.PossibleItemStacks.NormalItemDrop;
import BlockBreak.Rewards.RewardSound;
import BlockBreak.Rewards.RewardsHelper.Amount;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class ItemDrop {
    public static void performItemDrop(BlockClicker plugin, RewardSound sound, Map<?, ?> rewardData, GlobalFlags flags, Player player, Block block, Location location, ItemStack toolUsed){

        String brokenBlockName = block.getType().name();

        //get all possibly needed data for a drop
        String itemName = (String) rewardData.get("item");
        boolean isSpecialItem = Boolean.TRUE.equals(rewardData.get("is-custom"));
        Number chanceNr = (Number) rewardData.get("chance");

        if (itemName == null) {
            plugin.getLogger().warning("[Config Error] Missing item in " + brokenBlockName);
            return;
        }

        double chance = chanceNr != null ? chanceNr.doubleValue() : 100;
        int amount = Amount.getAmount(rewardData);

        //handle the Special case that XP should be dropped
        if(itemName.equals("xp")){
            XpDrop.performXpDrop(flags, player, location, amount, chance, toolUsed);
            return;
        }

        // get the possible dropping Stack
        ItemStack reward = isSpecialItem ?
                CustomItemDrop.rollCustomItem(plugin, rewardData, flags, player, toolUsed, itemName, amount, chance):
                NormalItemDrop.rollNormalItem(plugin, rewardData, flags, player, toolUsed, itemName, amount, chance);

        if (reward != null){
            if (flags.depositToInventory) player.getInventory().addItem(reward);
            else location.getWorld().dropItemNaturally(location, reward);
            sound.setSound(rewardData);
        }
    }
}
