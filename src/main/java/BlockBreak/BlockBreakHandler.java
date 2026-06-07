package BlockBreak;

import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

class BlockBreakHandler {
    protected static void onBlockBreakInZone (BlockClicker plugin, FileConfiguration config, Player player, Block block, Location location) {

        //read global flags
        String globalFlagPath = "global-flags.";
        boolean depositToInventory = config.getBoolean(globalFlagPath + "deposit-to-inventory");

        //gets the tool used to break the block
        ItemStack toolUsed = player.getInventory().getItemInMainHand();

        ConfigurationSection blockRewardsRoot = config.getConfigurationSection("block-rewards");
        if (blockRewardsRoot == null) return;

        //iterate through all possible tool groups
        for(String groupKey: blockRewardsRoot.getKeys(false)){

            //if the used tool is not in the current group, skip.
            if(!toolIsAllowedCheck.checkTool(config, toolUsed,groupKey)) continue;

            //Perform the drops logic in a subclass
            handleDrops.handleGroupDrops(plugin,config,player, block, location,depositToInventory, groupKey);
        }

    }
}
