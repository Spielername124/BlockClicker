package BlockBreak.RewardManagement.Rewards.ContainerSpawn.ItemManagement;

import BlockBreak.GlobalFlags;
import BlockBreak.RewardManagement.Rewards.PossibleItemStacks.CustomItemDrop;
import BlockBreak.RewardManagement.Rewards.PossibleItemStacks.DroppedItem;
import BlockBreak.RewardManagement.Rewards.PossibleItemStacks.NormalItemDrop;
import BlockBreak.RewardManagement.Rewards.RewardsHelper.Amount;
import BlockBreak.RewardManagement.Rewards.RewardsHelper.Chance;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;



public class ContainerSimpleItem implements ContainerItem {

        private final BlockClicker plugin;
        private final double chance;
        private final boolean isLuckDependent;
        private final DroppedItem item;

        public ContainerSimpleItem(BlockClicker plugin, Map<?, ?> rewardData) {
            this.plugin = plugin;
            Number chanceNr = (Number) rewardData.get("chance");
            this.chance = chanceNr != null ? chanceNr.doubleValue() : 100.0;
            this.isLuckDependent = Boolean.TRUE.equals(rewardData.get("luck-dependence"));

            boolean isSpecialItem = Boolean.TRUE.equals(rewardData.get("is-custom"));
            this.item = isSpecialItem ?  new CustomItemDrop(plugin, rewardData) : new NormalItemDrop(rewardData);
        }

    @Override
    public ItemStack rollPossibleItem(GlobalFlags flags, Player player, ItemStack toolUsed, int recursionDepth) {

        if (!Chance.performDropRoll(flags, chance, toolUsed, player, isLuckDependent)) {
            return null;
        }
        return item.getItem();
    }
}
