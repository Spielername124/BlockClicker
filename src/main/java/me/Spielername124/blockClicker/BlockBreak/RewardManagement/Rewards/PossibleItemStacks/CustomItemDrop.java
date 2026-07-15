package me.Spielername124.blockClicker.BlockBreak.RewardManagement.Rewards.PossibleItemStacks;

import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class CustomItemDrop extends DroppedItem {

    private final BlockClicker plugin;
    public CustomItemDrop(BlockClicker plugin, Map<?, ?> rewardData){
        super(rewardData);
        this.plugin = plugin;
    }

    @Override
    public ItemStack getItemStack() {
        return plugin.getItemsConfig().getItemStack("saved-items." + itemType);
    }
}

