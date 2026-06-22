package BlockBreak.RewardManagement.Rewards.ContainerSpawn;

import BlockBreak.GlobalFlags;
import BlockBreak.RewardManagement.Rewards.ContainerSpawn.ItemManagement.ContainerItem;
import BlockBreak.RewardManagement.Rewards.ContainerSpawn.ItemManagement.ContainerItemCreator;
import BlockBreak.RewardManagement.Rewards.ContainerSpawn.ItemManagement.ContainerSimpleItem;
import BlockBreak.RewardManagement.Rewards.Reward;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static BlockBreak.RewardManagement.Rewards.ContainerSpawn.ContainerHelper.doesContainerExist;


public class ContainerDrop extends Reward {
    private final List<ContainerItem> precalculatedItems = new ArrayList<>();
    private final String containerName;
    private Material material;
    private boolean shuffledSlots;

    private boolean valid = false;

    public ContainerDrop(BlockClicker plugin, FileConfiguration config, Map<?, ?> rewardData) {
        super(plugin, config, rewardData);

        this.containerName = (String) rewardData.get("container");


        ConfigurationSection containerSection = config.getConfigurationSection("findable-containers." + containerName);
        if (containerSection == null) {
            plugin.getLogger().warning("[Config Error] Container definition for '" + containerName + "' could not be found.");
            return;
        }
        //get the type of container that should be spawned
        String materialString = containerSection.getString("container-type");
        if(materialString==null) materialString="";

        //set the material, default to a basic chest
        material = Material.matchMaterial(materialString) != null ? Material.matchMaterial(materialString) : Material.CHEST ;

        //get whether it should be shuffled or not
        shuffledSlots = containerSection.getBoolean("shuffle",true);

        //validates that the config was readable from the metadata point of view
        this.valid = true;

        //find the related loot table for the container
        if (this.containerName != null && config.contains("findable-containers." + this.containerName)) {
            List<Map<?, ?>> lootConfigList = config.getMapList("findable-containers." + this.containerName + ".loot");

            //convert the loot table in possible items/guaranted rewards that drop items
            for (Map<?, ?> elementData : lootConfigList) {
                ContainerItem element = ContainerItemCreator.createPossibleItem(plugin, config, elementData);
                if (element != null) {
                    precalculatedItems.add(element);
                }
            }
        }
    }

    protected void execute (Player player, Location location, GlobalFlags flags, ItemStack toolUsed, Block block){
        //refuses to place a container with missing metadata
        if (!valid) {
            return;
        }

        //don't create a chest if this drop already spawned a chest
        if(!flags.containerHasBeenPlaced) {
            flags.containerHasBeenPlaced = true;
            // perform the Chest creation
            performContainerDrop(plugin, config, flags, player, toolUsed, containerName, block);
        }
    }

    public void performContainerDrop(BlockClicker plugin, FileConfiguration config, GlobalFlags flags, Player player, ItemStack toolUsed, String containerName, Block block){

        //schedule a new Task so that the chest gets placed in the next tick
        plugin.getServer().getScheduler().runTask(plugin, () -> {

            // creates a chest at the location of the previous
            block.setType(material, false);

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

            //create the list of free inventory space
            LinkedList<Integer> freeContainerSlots = ContainerHelper.possibleContainerSlots(containerInventory.getSize(), shuffledSlots);

            //roll the items
            for (ContainerItem lootElement : precalculatedItems) {
                if (freeContainerSlots.isEmpty()) break;

                ItemStack rolledItem = lootElement.rollPossibleItem(flags, player, toolUsed, 0);
                if (rolledItem != null) {
                    containerInventory.setItem(freeContainerSlots.poll(), rolledItem);
                }
            }
        });
    }
}
