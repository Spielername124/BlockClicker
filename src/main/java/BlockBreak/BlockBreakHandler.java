package BlockBreak;

import BlockBreak.RewardManagement.RewardCache;
import BlockBreak.RewardManagement.Rewards.HandleRewards;
import BlockBreak.ToolManagement.ToolCache;
import BlockBreak.ZoneManagement.ZoneCache;
import BlockBreak.ZoneManagement.ZoneGroup;
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
    protected static void checkAreas (BlockClicker plugin, FileConfiguration config, GlobalFlags flags, RewardCache rewardCache, ToolCache toolCache, ZoneCache zoneCache, Player player, Block block, Location location) {
        // Iterate through zone-groups
        for (ZoneGroup zoneGroup : zoneCache.getCachedZoneGroups()) {
            if (zoneGroup.isInZone(location)) {
                onBlockBreakInZone(plugin, config, rewardCache, toolCache, flags, player, block, location, zoneGroup.getName());
                //return if the zone groups are supposed to be mutually exclusive
                if (flags.mutuallyExclusiveRegions) {
                    return;
                }
            }
        }
    }

    private static void onBlockBreakInZone (BlockClicker plugin, FileConfiguration config, RewardCache rewardCache, ToolCache toolCache, GlobalFlags flags, Player player, Block block, Location location, String zoneGroup){

        //gets the tool used to break the block
        ItemStack toolUsed = player.getInventory().getItemInMainHand();

        //iterate through all possible tool groups
        for(String groupKey: toolCache.getAllToolGroups()){

            //if the used tool is not in the current group, skip.
            if(!toolCache.isToolAllowed(groupKey,toolUsed)) continue;

            //Perform the drops logic in a subclass
            HandleRewards.handleGroupDrops(plugin, rewardCache, flags, player, block, location, toolUsed, groupKey, zoneGroup);

            //return if the tool groups are supposed to be mutually exclusive
            if(flags.mutuallyExclusiveTools)
                return;
        }
    }

}
