package BlockBreak.Rewards.ContainerSpawn;

import BlockBreak.GlobalFlags;
import BlockBreak.Rewards.RewardSound;
import BlockBreak.Rewards.RewardsHelper.Chance;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static BlockBreak.Rewards.ContainerSpawn.ContainerHelper.doesContainerExist;


public class ContainerDrop {

    public static void rollContainerDrop(BlockClicker plugin, FileConfiguration config, RewardSound sound, Map<?, ?> rewardData, GlobalFlags flags, Player player, ItemStack toolUsed, Block block){

        String containerName = (String) rewardData.get("container");

        //if either no name is provided, or the chest is not defined, return
        if(containerName== null || !doesContainerExist(config, containerName)) return;

        if(!flags.containerHasBeenPlaced) {
            flags.containerHasBeenPlaced = true;
            // perform the Chest creation
            performContainerDrop(plugin, config, flags, player, toolUsed, containerName, block);
            sound.setSound(rewardData);
        }
    }

    public static void performContainerDrop(BlockClicker plugin, FileConfiguration config, GlobalFlags flags, Player player, ItemStack toolUsed, String containerName, Block block){

        ConfigurationSection containerSection = config.getConfigurationSection("findable-containers." + containerName);

        //get the type of container that should be spawned
        String materialString = containerSection.getString("container-type");
        Material containerMaterial = Material.matchMaterial(materialString);
        //default to normal chests
        if (containerMaterial == null) containerMaterial = Material.CHEST;
        Material finalContainerMaterial = containerMaterial;

        List<Map<?, ?>> possibleContainedItems = config.getMapList("findable-containers." + containerName +".loot");

        //get whether it should be shuffled or not
        boolean shuffledSlots = containerSection.getBoolean("shuffle",true);


        //schedule a new Task so that the chest gets placed in the next tick
        plugin.getServer().getScheduler().runTask(plugin, () -> {

            // creates a chest at the location of the previous
            block.setType(finalContainerMaterial, false);

            // ensure it, if it is a chest, does not merge with another chest
            if (block.getBlockData() instanceof org.bukkit.block.data.type.Chest chestData) {
                chestData.setType(org.bukkit.block.data.type.Chest.Type.SINGLE);
                block.setBlockData(chestData);
            }

            //update the Chests State
            block.getState().update(true);


            // get the Chest Inventory
            org.bukkit.block.Container containerState = (org.bukkit.block.Container) block.getState();
            Inventory containerInventory = containerState.getInventory();

            LinkedList<Integer> freeContainerSlots = ContainerHelper.possibleContainerSlots(containerInventory.getSize(), shuffledSlots);

            for(Map<?, ?> rewardData : possibleContainedItems) {
                if(freeContainerSlots.isEmpty()) break;

                ItemStack rolledItem = ContainerItems.rollPossibleItem(plugin, rewardData, flags, player, toolUsed);

                if(rolledItem != null){
                    containerInventory.setItem(freeContainerSlots.poll(), rolledItem);
                }
            }
        });
    }
}
