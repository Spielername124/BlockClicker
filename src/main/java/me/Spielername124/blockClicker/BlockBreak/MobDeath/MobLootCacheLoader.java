package me.Spielername124.blockClicker.BlockBreak.MobDeath;

import me.Spielername124.blockClicker.BlockBreak.RewardManagement.RewardCreator;
import me.Spielername124.blockClicker.BlockBreak.RewardManagement.Rewards.PossibleItemStacks.CustomItemDrop;
import me.Spielername124.blockClicker.BlockBreak.RewardManagement.Rewards.PossibleItemStacks.DroppedItem;
import me.Spielername124.blockClicker.BlockBreak.RewardManagement.Rewards.PossibleItemStacks.NormalItemDrop;
import me.Spielername124.blockClicker.BlockBreak.RewardManagement.Rewards.Reward;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.Map;

public class MobLootCacheLoader {
    public static void LoadAllMobLootTables(BlockClicker plugin, FileConfiguration config, MobLootCache lootCache) {
        lootCache.clear();
        //get the mobs section
        ConfigurationSection mobLootSection = config.getConfigurationSection("mob-loot");
        if (mobLootSection == null) return;

        //iterate through every mob
        for (String mobName : mobLootSection.getKeys(false)) {
            List<Map<?, ?>> lootList = mobLootSection.getMapList(mobName);
            if (lootList.isEmpty()) continue;

            //convert every possible item to a reward and register it in the cache
            for (Map<?, ?> rewardData : lootList) {

                boolean isSpecialItem = Boolean.TRUE.equals(rewardData.get("is-custom"));
                String itemName = (String) rewardData.get("item");

                DroppedItem rewardItem = isSpecialItem ? new CustomItemDrop(plugin, rewardData) : new NormalItemDrop(rewardData);

                lootCache.registerMobDrop(mobName, rewardItem);
            }
        }
    }
}
