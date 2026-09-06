package me.Spielername124.blockClicker.RewardManagement.Rewards.ItemDrop;

import me.Spielername124.blockClicker.EventWideFlags;
import me.Spielername124.blockClicker.GlobalFlags;
import me.Spielername124.blockClicker.RewardManagement.Rewards.PossibleItemStacks.DroppedItem;
import me.Spielername124.blockClicker.RewardManagement.Rewards.Reward;
import me.Spielername124.blockClicker.RewardManagement.Rewards.RewardSoundAndParticle;
import me.Spielername124.blockClicker.RewardManagement.Rewards.RewardsHelper.Amount;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Map;

public class ItemDrop extends Reward {

    private final DroppedItem droppedItem;
    private final Amount amount;
    private final String itemName;


    public ItemDrop(BlockClicker plugin, FileConfiguration config, Map<?, ?> rewardData) {
        super(plugin, config, rewardData);
        amount = new Amount(rewardData);

        itemName = (String) rewardData.get("item");

        //creates a DroppedItem of the correct kind or returns null if the item is
        droppedItem = DroppedItem.create(plugin, rewardData);
    }

    protected void execute(Player player, Location location, GlobalFlags flags, RewardSoundAndParticle sound, ItemStack toolUsed, Block block, EventWideFlags eventWideFlags) {

        //handle the Special case that XP should be dropped
        if (itemName.equals("xp")) {
            XpDrop.performXpDrop(flags, player, location, amount.getAmount(), toolUsed);
            return;
        }

        ItemStack reward = droppedItem.getItem();

        if (reward != null && reward.getAmount() >= 0) {
            if (flags.depositToInventory) {
                if (player != null)
                    performDepositToInventory(player, reward);
            } else location.getWorld().dropItemNaturally(location, reward);
        }
    }


    // handles the depositing directly to the inventory.
    private void performDepositToInventory(Player player, ItemStack item) {
        PlayerInventory inventory = player.getInventory();
        ItemStack offHand = inventory.getItemInOffHand();

        if (offHand.isSimilar(item)) {
            int spaceLeft = offHand.getMaxStackSize() - offHand.getAmount();
            if (spaceLeft > 0) {
                int toAdd = Math.min(spaceLeft, item.getAmount());
                offHand.setAmount(offHand.getAmount() + toAdd);
                item.setAmount(item.getAmount() - toAdd);
            }
        }

        if (item.getAmount() > 0) {
            inventory.addItem(item);
        }
    }
}
