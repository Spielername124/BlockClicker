package me.Spielername124.blockClicker.RewardManagement.Rewards.ContainerSpawn.ItemManagement;

import me.Spielername124.blockClicker.GlobalFlags;
import me.Spielername124.blockClicker.RewardManagement.Rewards.RewardsHelper.Chance;
import me.Spielername124.blockClicker.BlockClicker;
import me.Spielername124.blockClicker.RewardManagement.Rewards.RewardsHelper.WeightedList;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public class ContainerGuaranteedItem implements ContainerItem {
    private final BlockClicker plugin;
    private final double chance;
    private final boolean isLuckDependent;
    private final WeightedList<ContainerItem> weightedItemPool = new WeightedList<>();

    public ContainerGuaranteedItem(BlockClicker plugin, FileConfiguration config, Map<?, ?> rewardData) {
        this.plugin = plugin;

        //get the chance
        Number chanceNr = (Number) rewardData.get("chance");
        this.chance = chanceNr != null ? chanceNr.doubleValue() : 100.0;
        this.isLuckDependent = Boolean.TRUE.equals(rewardData.get("luck-dependence"));

        //get all the guaranteed Reward items
        Object rawReward = rewardData.get("guaranteed-reward");

        Object rawList = null;
        if (rawReward instanceof Map<?, ?> rewardMap) {
            rawList = rewardMap.get("rewards");
        } else if (rawReward instanceof List) {
            rawList = rawReward;
        }

        if (rawList instanceof List) {
            for (Object obj : (List<?>) rawList) {
                if (obj instanceof Map<?, ?> innerData) {

                    //send it through the ItemCreator and put it into the saving Lists
                    ContainerItem element = ContainerItemCreator.createPossibleItem(plugin, config, innerData);
                    if (element != null) {
                        Number weightNr = (Number) innerData.get("weight");
                        double weight = weightNr != null ? weightNr.doubleValue() : 1.0;
                        weightedItemPool.addElement(element, weight);
                    }
                }
            }
        }
    }

    @Override
    public ItemStack rollPossibleItem(GlobalFlags flags, Player player, ItemStack toolUsed, Block block, int recursionDepth) {
        //use the guaranteed reward roll logic
        if (recursionDepth > flags.recursionDepth) {
            plugin.getLogger().warning("Maximum recursion depth reached inside container loot.");
            return null;
        }

        if (!Chance.performDropRoll(flags, chance, toolUsed, player, block, isLuckDependent)) {
            return null;
        }

        if (weightedItemPool.isEmpty()) return null;

        ContainerItem chosenElement = weightedItemPool.getRandomElement();

        return chosenElement!= null?
                chosenElement.rollPossibleItem(flags, player, toolUsed, block, recursionDepth + 1):
                null;

    }
}

