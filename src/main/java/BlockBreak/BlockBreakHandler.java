package BlockBreak;

import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

class BlockBreakHandler {
    protected static void onBlockBreakInZone (BlockClicker plugin, FileConfiguration config, Player player, Block block){
        String brokenBlockName = block.getType().name();
        String path = "block-rewards." + brokenBlockName;

        //returns if the block has no specified rewards
        if (!config.contains(path)) {
            return;
        }

        //give the specified amount of XP to the player
        int xp = config.getInt(path + ".xp", 0);
        player.giveExp(xp);

        List<Map<?, ?>> possibleRewards = config.getMapList("block-rewards." + brokenBlockName + ".rewards");

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


            double randomRoll = ThreadLocalRandom.current().nextDouble(100.0);;

            if(randomRoll <= chance){

                ItemStack reward = new ItemStack(rewardItem, amount);
                player.getInventory().addItem(reward);
            }
        }
    }
}
