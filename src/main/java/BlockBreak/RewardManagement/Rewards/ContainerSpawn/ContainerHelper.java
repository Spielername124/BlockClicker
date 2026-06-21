package BlockBreak.RewardManagement.Rewards.ContainerSpawn;

import org.bukkit.configuration.file.FileConfiguration;


import java.util.Collections;
import java.util.LinkedList;

public class ContainerHelper {
    public static LinkedList<Integer> possibleContainerSlots(int slots, boolean shuffled){

        LinkedList<Integer> inventory = new LinkedList<>();
        for(int i = 0; i < slots; i++){
            inventory.add(i);
        }
        if(shuffled) Collections.shuffle(inventory);
        return inventory;
    }

    public static boolean doesContainerExist(FileConfiguration config, String containerName){
        if (!config.contains("findable-containers")) {
            return false;
        }
        return config.contains("findable-containers." + containerName);
    }
}
