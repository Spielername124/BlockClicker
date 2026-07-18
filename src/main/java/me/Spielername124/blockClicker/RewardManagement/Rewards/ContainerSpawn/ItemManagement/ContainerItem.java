package me.Spielername124.blockClicker.RewardManagement.Rewards.ContainerSpawn.ItemManagement;

import me.Spielername124.blockClicker.GlobalFlags;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface ContainerItem {
    ItemStack rollPossibleItem(GlobalFlags flags, Player player, ItemStack toolUsed, Block block, int recursionDepth);
}
