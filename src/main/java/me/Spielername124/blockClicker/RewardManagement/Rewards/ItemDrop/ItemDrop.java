package me.Spielername124.blockClicker.RewardManagement.Rewards.ItemDrop;

import me.Spielername124.blockClicker.GlobalFlags;
import me.Spielername124.blockClicker.RewardManagement.Rewards.PossibleItemStacks.CustomItemDrop;
import me.Spielername124.blockClicker.RewardManagement.Rewards.PossibleItemStacks.DroppedItem;
import me.Spielername124.blockClicker.RewardManagement.Rewards.PossibleItemStacks.NormalItemDrop;
import me.Spielername124.blockClicker.RewardManagement.Rewards.Reward;
import me.Spielername124.blockClicker.RewardManagement.Rewards.RewardSound;
import me.Spielername124.blockClicker.RewardManagement.Rewards.RewardsHelper.Amount;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Objects;

public class ItemDrop extends Reward {

    private final DroppedItem droppedItem;
    private final Amount amount;
    private final String itemName;


    public ItemDrop(BlockClicker plugin, FileConfiguration config, Map<?, ?> rewardData) {
        super(plugin, config, rewardData);
        amount = new Amount(rewardData);
        boolean isSpecialItem = Boolean.TRUE.equals(rewardData.get("is-custom"));

        itemName = (String) rewardData.get("item");

        if(Objects.equals(itemName, "xp")) droppedItem=null;

        else{
            droppedItem = isSpecialItem ?  new CustomItemDrop(plugin, rewardData) : new NormalItemDrop(rewardData);
        }
    }

    protected void execute(Player player, Location location, GlobalFlags flags, RewardSound sound, ItemStack toolUsed, Block block){

        //handle the Special case that XP should be dropped
        if(itemName.equals("xp")){
            XpDrop.performXpDrop(flags, player, location, amount.getAmount(), toolUsed);
            return;
        }

        ItemStack reward = droppedItem.getItem();

        if (reward != null) {
            if (flags.depositToInventory){
                if (player != null)
                    player.getInventory().addItem(reward);
            }

            else location.getWorld().dropItemNaturally(location, reward);
        }
    }
}

