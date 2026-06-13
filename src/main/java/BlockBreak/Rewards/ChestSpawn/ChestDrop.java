package BlockBreak.Rewards.ChestSpawn;

import BlockBreak.GlobalFlags;
import BlockBreak.Rewards.RewardsHelper.Chance;
import me.Spielername124.blockClicker.BlockClicker;
import net.kyori.adventure.audience.ForwardingAudience;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Chest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static BlockBreak.Rewards.ChestSpawn.ChestHelper.doesChestExist;


public class ChestDrop {

    public static void rollChestDrop(BlockClicker plugin, FileConfiguration config, Map<?, ?> rewardData, GlobalFlags flags, Player player, ItemStack toolUsed, Block block){

        String chestName = (String) rewardData.get("chest");

        //if either no name is provided, or the chest is not defined, return
        if(chestName== null || !doesChestExist(config, chestName)) return;

        Number chanceNr = (Number) rewardData.get("chance");
        double chance = chanceNr != null ? chanceNr.doubleValue() : 100;

        // perform the Chest creation if the player rolled it
        if (Chance.performDropRoll(flags, chance, toolUsed, player)) performChestDrop(plugin, config, flags, player, toolUsed, chestName, block);
    }

    public static void performChestDrop(BlockClicker plugin, FileConfiguration config, GlobalFlags flags, Player player, ItemStack toolUsed, String chestName, Block block){
        List<Map<?, ?>> possibleContainedItems = config.getMapList("findable-Chests." + chestName);
        LinkedList<Integer> freeChestSlots = ChestHelper.randomPossibleChestSlots();

        //schedule a new Task so that the chest gets placed in the next tick
        plugin.getServer().getScheduler().runTask(plugin, () -> {
            // creates a chest at the location of the previous block
            block.setType(Material.CHEST, false);

            // ensure it does not merge with another chest
            org.bukkit.block.data.type.Chest chestData = (org.bukkit.block.data.type.Chest) block.getBlockData();
            chestData.setType(Chest.Type.SINGLE);
            block.setBlockData(chestData);

            //update the Chests State
            block.getState().update(true);


            // get the Chest Inventory
            org.bukkit.block.Chest chestState = (org.bukkit.block.Chest) block.getState();
            Inventory chestInventory = chestState.getInventory();

            for(Map<?, ?> rewardData : possibleContainedItems) {
                if(freeChestSlots.isEmpty()) break;

                ItemStack rolledItem = ChestItems.rollPossibleItem(plugin, rewardData, flags, player, toolUsed);

                if(rolledItem != null){
                    chestInventory.setItem(freeChestSlots.poll(), rolledItem);
                }
            }
        });

    }
}
