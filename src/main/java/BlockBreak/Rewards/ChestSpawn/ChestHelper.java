package BlockBreak.Rewards.ChestSpawn;

import org.bukkit.configuration.file.FileConfiguration;


import java.util.Collections;
import java.util.LinkedList;

public class ChestHelper {
    public static LinkedList<Integer> randomPossibleChestSlots(){
        LinkedList<Integer> slots = new LinkedList<>();
        for(int i = 0; i < 27; i++){
            slots.add(i);
        }
        Collections.shuffle(slots);
        return slots;
    }

    public static boolean doesChestExist(FileConfiguration config, String chestName){
        if (!config.contains("findable-Chests")) {
            return false;
        }
        return config.contains("findable-Chests." + chestName);
    }
}
