package me.Spielername124.blockClicker.BlockBreak.RewardManagement.Rewards.ContainerSpawn.ItemManagement;

import me.Spielername124.blockClicker.BlockBreak.GlobalFlags;
import me.Spielername124.blockClicker.BlockBreak.RewardManagement.Rewards.RewardsHelper.Chance;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class ContainerGuaranteedItem implements ContainerItem {
    private final BlockClicker plugin;
    private final double chance;
    private final boolean isLuckDependent;
    private final List<ContainerItem> pool = new ArrayList<>();
    private final List<Double> weights = new ArrayList<>();
    private double totalWeight = 0.0;

    public ContainerGuaranteedItem(BlockClicker plugin, FileConfiguration config, Map<?, ?> rewardData) {
        this.plugin = plugin;

        //get the chance
        Number chanceNr = (Number) rewardData.get("chance");
        this.chance = chanceNr != null ? chanceNr.doubleValue() : 100.0;
        this.isLuckDependent = Boolean.TRUE.equals(rewardData.get("luck-dependence"));

        //get all the guaranteed Reward items
        Object rawList = rewardData.get("guaranteed-reward");
        if (rawList instanceof List) {
            for (Object obj : (List<?>) rawList) {
                if (obj instanceof Map<?, ?> innerData) {

                    //send it through the ItemCreator and put it into the saving Lists
                    ContainerItem element = ContainerItemCreator.createPossibleItem(plugin, config, innerData);
                    if (element != null) {
                        pool.add(element);
                        Number weightNr = (Number) innerData.get("weight");
                        double weight = weightNr != null ? weightNr.doubleValue() : 1.0;
                        weights.add(weight);
                        totalWeight += weight;
                    }
                }
            }
        }
    }

    @Override
    public ItemStack rollPossibleItem(GlobalFlags flags, Player player, ItemStack toolUsed, int recursionDepth) {
        //use the guaranteed reward roll logic
        if (recursionDepth > flags.recursionDepth) {
            plugin.getLogger().warning("Maximum recursion depth reached inside container loot.");
            return null;
        }

        if (!Chance.performDropRoll(flags, chance, toolUsed, player, isLuckDependent)) {
            return null;
        }

        if (pool.isEmpty()) return null;

        double rolledWeight = ThreadLocalRandom.current().nextDouble(totalWeight);
        double currentWeight = 0.0;

        for (int i = 0; i < pool.size(); i++) {
            currentWeight += weights.get(i);
            if (currentWeight >= rolledWeight) {
                //use recursion to get the ItemStack no matter if it is nested or not
                return pool.get(i).rollPossibleItem(flags, player, toolUsed, recursionDepth + 1);
            }
        }
        return null;
    }
}

