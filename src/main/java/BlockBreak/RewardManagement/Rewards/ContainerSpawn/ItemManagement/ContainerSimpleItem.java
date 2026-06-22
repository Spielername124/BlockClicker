package BlockBreak.RewardManagement.Rewards.ContainerSpawn.ItemManagement;

import BlockBreak.GlobalFlags;
import BlockBreak.RewardManagement.Rewards.PossibleItemStacks.CustomItemDrop;
import BlockBreak.RewardManagement.Rewards.PossibleItemStacks.NormalItemDrop;
import BlockBreak.RewardManagement.Rewards.RewardsHelper.Amount;
import BlockBreak.RewardManagement.Rewards.RewardsHelper.Chance;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;



public class ContainerSimpleItem implements ContainerItem {

        private final BlockClicker plugin;
        private final Map<?, ?> rewardData;
        private final double chance;
        private final boolean isLuckDependent;
        private final String itemName;
        private final boolean isCustom;

        public ContainerSimpleItem(BlockClicker plugin, Map<?, ?> rewardData) {
            this.plugin = plugin;
            this.rewardData = rewardData;

            Number chanceNr = (Number) rewardData.get("chance");
            this.chance = chanceNr != null ? chanceNr.doubleValue() : 100.0;
            this.isLuckDependent = Boolean.TRUE.equals(rewardData.get("luck-dependence"));
            this.itemName = (String) rewardData.get("item");
            this.isCustom = Boolean.TRUE.equals(rewardData.get("is-custom"));
        }

    @Override
    public ItemStack rollPossibleItem(GlobalFlags flags, Player player, ItemStack toolUsed, int recursionDepth) {

        if (!Chance.performDropRoll(flags, chance, toolUsed, player, isLuckDependent)) {
            return null;
        }

        if (itemName == null) return null;
        int amount = Amount.getAmount(rewardData);

        return isCustom ?
                CustomItemDrop.getCustomItem(plugin, itemName, amount) :
                NormalItemDrop.getNormalItem(itemName, amount);
    }
}
