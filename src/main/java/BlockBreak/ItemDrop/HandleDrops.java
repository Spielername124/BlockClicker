package BlockBreak.ItemDrop;

import BlockBreak.Chance;
import BlockBreak.GlobalFlags;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import java.util.Map;

public class HandleDrops {
    public static void handleGroupDrops(BlockClicker plugin, FileConfiguration config, GlobalFlags flags, Player player, Block block, Location location, ItemStack toolUsed, String parentGroup){
        String brokenBlockName = block.getType().name();
        String path = "block-rewards." + parentGroup + "." + brokenBlockName;

        //returns if the block has no specified rewards
        if (!config.contains(path)) {
            return;
        }

        //give the specified amount of XP to the player / or drop it
        int xp = config.getInt(path + ".xp", 0);
        if(xp>0) {
            if (flags.depositToInventory) player.giveExp(xp);
            else {
                ExperienceOrb xpDrop = location.getWorld().spawn(location, ExperienceOrb.class);
                xpDrop.setExperience(xp);
            }
        }

        List<Map<?, ?>> possibleRewards = config.getMapList(path + ".rewards");

        // Iterating through every possible reward for the broken block
        for(Map<?, ?> rewardData : possibleRewards){

            if(rewardData.containsKey("custom-item")){
                HandleCustomItemDrop.rollCustomItem(plugin, rewardData, flags, player, location, toolUsed);
                continue;
            }
            HandleNormalItemDrop.rollNormalItem(plugin, rewardData, flags, player, location, toolUsed, brokenBlockName);

        }
    }
}
