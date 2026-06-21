package BlockBreak.RewardManagement;

import BlockBreak.RewardManagement.Rewards.Reward;
import BlockBreak.RewardManagement.Rewards.RewardSound;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.Map;

public class RewardCacheLoader {

    public static void loadAllLootTables(BlockClicker plugin, FileConfiguration config, RewardCache lootCache) {
        lootCache.clear();

        //get the zoneGroups
        ConfigurationSection zoneGroupsSection = config.getConfigurationSection("zone-groups");
        if (zoneGroupsSection == null) return;

        // iterates through the different zone group reward section
        for (String zoneGroupName : zoneGroupsSection.getKeys(false)) {
            ConfigurationSection zoneSection = config.getConfigurationSection(zoneGroupName);
            if (zoneSection == null) continue;

            // Iterates through the different tool groups in a zone group reward section
            for (String toolGroupName : zoneSection.getKeys(false)) {
                ConfigurationSection toolSection = zoneSection.getConfigurationSection(toolGroupName);
                if (toolSection == null) continue;

                // iterate through the define materials
                for (String blockName : toolSection.getKeys(false)) {
                    Material material = Material.matchMaterial(blockName);
                    if (material == null) continue;

                    // creates the rewards and puts them into the cache
                    List<Map<?, ?>> rewardList = toolSection.getMapList(blockName);
                    for (Map<?, ?> rewardData : rewardList) {

                        Reward finalReward = RewardCreator.createReward(plugin, config, rewardData);

                        if (finalReward != null) {
                            lootCache.registerReward(zoneGroupName, toolGroupName, material, finalReward);
                        }
                    }
                }
            }
        }
    }
}
