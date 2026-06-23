package BlockBreak.RewardManagement.Rewards.ItemDrop;

import BlockBreak.GlobalFlags;
import BlockBreak.RewardManagement.Rewards.PossibleItemStacks.CustomItemDrop;
import BlockBreak.RewardManagement.Rewards.PossibleItemStacks.NormalItemDrop;
import BlockBreak.RewardManagement.Rewards.Reward;
import BlockBreak.RewardManagement.Rewards.RewardSound;
import BlockBreak.RewardManagement.Rewards.RewardsHelper.Amount;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class ItemDrop extends Reward {
    public ItemDrop(BlockClicker plugin, FileConfiguration config, Map<?, ?> rewardData) {
        super(plugin, config, rewardData);
    }

    protected void execute(Player player, Location location, GlobalFlags flags, RewardSound sound, ItemStack toolUsed, Block block){
        String brokenBlockName = block.getType().name();

        //get all possibly needed data for a drop
        String itemName = (String) rewardData.get("item");
        boolean isSpecialItem = Boolean.TRUE.equals(rewardData.get("is-custom"));

        if (itemName == null) {
            plugin.getLogger().warning("[Config Error] Missing item in " + brokenBlockName);
            return;
        }

        int amount = Amount.getAmount(rewardData);

        //handle the Special case that XP should be dropped
        if(itemName.equals("xp")){
            XpDrop.performXpDrop(flags, player, location, amount, toolUsed);
            return;
        }

        // gets the item stack
        ItemStack reward = isSpecialItem ?
                CustomItemDrop.getCustomItem(plugin, itemName, amount) :
                NormalItemDrop.getNormalItem(itemName, amount);

        if (reward != null) {
            if (flags.depositToInventory) player.getInventory().addItem(reward);
            else location.getWorld().dropItemNaturally(location, reward);
        }
    }
}

