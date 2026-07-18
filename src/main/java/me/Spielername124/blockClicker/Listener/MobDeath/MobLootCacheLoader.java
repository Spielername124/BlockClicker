package me.Spielername124.blockClicker.Listener.MobDeath;

import me.Spielername124.blockClicker.RewardManagement.RewardCreator;
import me.Spielername124.blockClicker.RewardManagement.Rewards.Reward;
import me.Spielername124.blockClicker.BlockClicker;
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

            ConfigurationSection mobSection = mobLootSection.getConfigurationSection(mobName);
            if (mobSection == null) continue;

            //register the natural drop flag
            boolean naturalDrops = mobSection.getBoolean("allows-natural-drops", true);
            lootCache.registerMobFlags(mobName, naturalDrops);

            List<Map<?, ?>> lootList = mobSection.getMapList("drops");
            if (lootList.isEmpty()) continue;

            //convert every possible item to a reward and register it in the cache
            for (Map<?, ?> rewardData : lootList) {
                //create the reward
                Reward compiledReward = RewardCreator.createReward(plugin, config, rewardData);
                if (compiledReward != null) {
                    lootCache.registerMobDrop(mobName, compiledReward);
                }
            }
        }
    }
}
