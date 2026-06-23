package BlockBreak;

import BlockBreak.RewardManagement.RewardCache;
import BlockBreak.RewardManagement.Rewards.HandleRewards;
import BlockBreak.ToolManagement.ToolCache;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

class BlockBreakHandler {
    protected static void checkAreas (BlockClicker plugin, FileConfiguration config, RewardCache rewardCache, ToolCache toolCache, Player player, Block block, Location location) {
        //read the global flags
        GlobalFlags flags = new GlobalFlags(config);

        ConfigurationSection zoneGroupsSection = config.getConfigurationSection("zone-groups");
        if (zoneGroupsSection == null) return;

        //iterate trough every possible zone-group
        for (String zoneGroupName : zoneGroupsSection.getKeys(false)) {
            ConfigurationSection zoneGroup = zoneGroupsSection.getConfigurationSection(zoneGroupName);
            if (zoneGroup == null) continue;

            //if a zone-group is everywhere possible, let the
            if (zoneGroup.getBoolean("everywhere", false)) {
                onBlockBreakInZone(plugin, config, rewardCache,toolCache, flags, player, block, location, zoneGroupName);
                if(flags.mutuallyExclusiveRegions){
                    return;
                }
                continue;
            }

            //check all region Id's of the zone group and proceed if the broken block is contained in one of the regions
            if (zoneGroup.contains("region-ids")) {
                List<String> regionIds = zoneGroup.getStringList("region-ids");
                boolean isInZone = false;

                for (String id : regionIds) {
                    RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
                    RegionManager regions = container.get(BukkitAdapter.adapt(location.getWorld()));

                    if (regions != null) {
                        ProtectedRegion targetRegion = regions.getRegion(id);
                        if (targetRegion != null && targetRegion.contains(BlockVector3.at(location.getBlockX(), location.getBlockY(), location.getBlockZ()))) {
                            isInZone = true;
                            break;
                        }
                    }
                }

                if (isInZone) {
                    onBlockBreakInZone(plugin, config, rewardCache, toolCache, flags, player, block, location, zoneGroupName);
                    if(flags.mutuallyExclusiveRegions){
                        return;
                    }
                }
            }
        }
    }

    private static void onBlockBreakInZone (BlockClicker plugin, FileConfiguration config, RewardCache rewardCache, ToolCache toolCache, GlobalFlags flags, Player player, Block block, Location location, String zoneGroup){

        //gets the tool used to break the block
        ItemStack toolUsed = player.getInventory().getItemInMainHand();

        ConfigurationSection blockRewardsRoot = config.getConfigurationSection(zoneGroup);
        if (blockRewardsRoot == null) return;

        //iterate through all possible tool groups
        for(String groupKey: blockRewardsRoot.getKeys(false)){

            //if the used tool is not in the current group, skip.
            if(!toolCache.isToolAllowed(groupKey,toolUsed)) continue;

            //Perform the drops logic in a subclass
            HandleRewards.handleGroupDrops(plugin, rewardCache, flags, player, block, location, toolUsed, groupKey, zoneGroup);
        }
    }

}
