package BlockBreak;

import org.bukkit.configuration.file.FileConfiguration;

public class GlobalFlags {
    boolean depositToInventory = false;
    double fortuneMultiplier = 0;

    public GlobalFlags(FileConfiguration config){
        String globalFlagPath = "global-flags.";
        depositToInventory = config.getBoolean(globalFlagPath + "deposit-to-inventory");
        fortuneMultiplier = config.getDouble(globalFlagPath + "fortune-modifier-per-level");
    }
}
