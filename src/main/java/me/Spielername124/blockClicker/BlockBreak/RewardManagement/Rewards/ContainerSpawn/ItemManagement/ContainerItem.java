package me.Spielername124.blockClicker.BlockBreak.RewardManagement.Rewards.ContainerSpawn.ItemManagement;

import me.Spielername124.blockClicker.BlockBreak.GlobalFlags;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface ContainerItem {
    ItemStack rollPossibleItem(GlobalFlags flags, Player player, ItemStack toolUsed, int recursionDepth);
}
