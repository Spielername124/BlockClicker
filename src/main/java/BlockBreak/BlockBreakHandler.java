package BlockBreak;

import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

class BlockBreakHandler {
    protected static void onBlockBreakInZone (BlockClicker plugin, FileConfiguration config, Player player, Block block, Location location) {

        //get the global Flags:
        GlobalFlags flags = new GlobalFlags(config);

        //gets the tool used to break the block
        ItemStack toolUsed = player.getInventory().getItemInMainHand();

        ConfigurationSection blockRewardsRoot = config.getConfigurationSection("block-rewards");
        if (blockRewardsRoot == null) return;

        //iterate through all possible tool groups
        for(String groupKey: blockRewardsRoot.getKeys(false)){

            //if the used tool is not in the current group, skip.
            if(!ToolIsAllowedCheck.checkTool(config, toolUsed,groupKey)) continue;

            //Perform the drops logic in a subclass
            HandleDrops.handleGroupDrops(plugin, config, flags, player, block, location, groupKey);
        }

    }
}
