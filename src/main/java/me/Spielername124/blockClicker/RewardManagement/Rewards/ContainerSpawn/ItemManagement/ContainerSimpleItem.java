package me.Spielername124.blockClicker.RewardManagement.Rewards.ContainerSpawn.ItemManagement;

import me.Spielername124.blockClicker.GlobalFlags;
import me.Spielername124.blockClicker.RewardManagement.Rewards.PossibleItemStacks.CustomItemDrop;
import me.Spielername124.blockClicker.RewardManagement.Rewards.PossibleItemStacks.DroppedItem;
import me.Spielername124.blockClicker.RewardManagement.Rewards.PossibleItemStacks.NormalItemDrop;
import me.Spielername124.blockClicker.RewardManagement.Rewards.RewardsHelper.Chance;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

import static me.Spielername124.blockClicker.Helper.MapParser.getLuckModifierDependence;


public class ContainerSimpleItem implements ContainerItem {

        private final BlockClicker plugin;
        private final double chance;
        private final Chance.LuckModifierDependence luckModifierDependence;
        private final DroppedItem item;

        public ContainerSimpleItem(BlockClicker plugin, Map<?, ?> rewardData) {
            this.plugin = plugin;
            Number chanceNr = (Number) rewardData.get("chance");
            this.chance = chanceNr != null ? chanceNr.doubleValue() : 100.0;
            luckModifierDependence = getLuckModifierDependence(rewardData,"luck-modifier-dependence", Chance.LuckModifierDependence.NORMAL);

            boolean isSpecialItem = Boolean.TRUE.equals(rewardData.get("is-custom"));
            this.item = isSpecialItem ?  new CustomItemDrop(plugin, rewardData) : new NormalItemDrop(rewardData);
        }

    @Override
    public ItemStack rollPossibleItem(GlobalFlags flags, Player player, ItemStack toolUsed, Block block, int recursionDepth) {

        if (!Chance.performDropRoll(flags, chance, toolUsed, player, block, luckModifierDependence)) {
            return null;
        }
        return item.getItem();
    }
}
