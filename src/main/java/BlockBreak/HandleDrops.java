package BlockBreak;

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
import java.util.concurrent.ThreadLocalRandom;

public class HandleDrops {
    public static void handleGroupDrops(BlockClicker plugin, FileConfiguration config, GlobalFlags flags, Player player, Block block, Location location, String parentGroup){
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

            //get the specifics for possible reward
            String itemName = (String) rewardData.get("item");
            Number amountNr = (Number) rewardData.get("amount");
            Number chanceNr = (Number) rewardData.get("chance");

            if(itemName == null || chanceNr == null) {
                plugin.getLogger().warning("[Config Error] Missing item, or chance for reward in " + brokenBlockName);
                continue;
            }

            int amount = 1;
            if (amountNr!=null) amount = amountNr.intValue();
            double chance = chanceNr.doubleValue();
            Material rewardItem = Material.matchMaterial(itemName);

            if (rewardItem == null) {
                plugin.getLogger().warning("[Config Error] Invalid material name '" + itemName +
                        "' found under block-rewards." + brokenBlockName);
                continue;
            }

            if(Chance.performDropRoll(flags, chance)){
                ItemStack reward = new ItemStack(rewardItem, amount);

                if(flags.depositToInventory) player.getInventory().addItem(reward);
                else location.getWorld().dropItemNaturally(location, reward);
            }
        }
    }
}
