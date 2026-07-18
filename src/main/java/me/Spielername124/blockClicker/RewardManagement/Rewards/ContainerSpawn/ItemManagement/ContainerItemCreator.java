package me.Spielername124.blockClicker.RewardManagement.Rewards.ContainerSpawn.ItemManagement;

import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Map;

public class ContainerItemCreator {
    //create droppable items
    public static ContainerItem  createPossibleItem (BlockClicker plugin, FileConfiguration config, Map<?, ?> elementData) {
        if (elementData.containsKey("guaranteed-reward")) {
            return new ContainerGuaranteedItem(plugin, config, elementData);
        } else if (elementData.containsKey("item")) {
            return new ContainerSimpleItem(plugin, elementData);
        }
        return null;
    }
}
