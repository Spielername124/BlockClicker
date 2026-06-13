package BlockBreak;

import BlockBreak.Rewards.HandleRewards;
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
import java.util.Map;

class BlockBreakHandler {
    protected static void checkAreas (BlockClicker plugin, FileConfiguration config, Player player, Block block, Location location) {
        //iter
        for (Map<?, ?> zoneGroup : config.getMapList("zone-groups")) {
            String zoneGroupName = (String) zoneGroup.get("regionName");

            boolean isEverywhere = Boolean.TRUE.equals(zoneGroup.get("everywhere"));
            if (isEverywhere) {
                onBlockBreakInZone(plugin, config, player, block, location, zoneGroupName);
                return;
            }


            if (zoneGroup.containsKey("region-ids")) {
                List<String> regionIds = (List<String>) zoneGroup.get("region-ids");

                //iterate through the zones to look if one contains the block
                for (String id : regionIds) {
                    //get the Zone from Worldguard (if it Exists)
                    RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
                    RegionManager regions = container.get(BukkitAdapter.adapt(location.getWorld()));
                    if (regions != null) {
                        ProtectedRegion targetRegion = regions.getRegion(id);
                        if (targetRegion != null && targetRegion.contains(BlockVector3.at(location.getBlockX(), location.getBlockY(), location.getBlockZ()))) {
                            onBlockBreakInZone(plugin, config, player, block, location, zoneGroupName);
                            return;
                        }
                    }
                }
            }
        }
    }

    private static void onBlockBreakInZone (BlockClicker plugin, FileConfiguration config, Player player, Block block, Location location, String zoneGroup){
        //read the global flags
        GlobalFlags flags = new GlobalFlags(config);

        //gets the tool used to break the block
        ItemStack toolUsed = player.getInventory().getItemInMainHand();

        ConfigurationSection blockRewardsRoot = config.getConfigurationSection(zoneGroup);
        if (blockRewardsRoot == null) return;

        //iterate through all possible tool groups
        for(String groupKey: blockRewardsRoot.getKeys(false)){

            //if the used tool is not in the current group, skip.
            if(!ToolIsAllowedCheck.checkTool(config, toolUsed,groupKey)) continue;

            //Perform the drops logic in a subclass
            HandleRewards.handleGroupDrops(plugin, config, flags, player, block, location, toolUsed, groupKey, zoneGroup);
        }
    }

}
