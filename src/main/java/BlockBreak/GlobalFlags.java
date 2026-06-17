package BlockBreak;

import org.bukkit.configuration.file.FileConfiguration;

public class GlobalFlags {
    public boolean mutuallyExclusiveRegions =false;
    public boolean depositToInventory = false;
    public double fortuneMultiplier = 1;
    public double luckMultiplier = 1;
    public double badLuckMultiplier = 1;
    public boolean interModifierMultiplicativity;
    public boolean intraModifierMultiplicativity;
    public boolean publicSound;

    //tracks if already a container was rewarded --> if so prevents another one from spawning
    public boolean containerHasBeenPlaced = false;

    public GlobalFlags(FileConfiguration config){
        String globalFlagPath = "global-flags.";
        depositToInventory = config.getBoolean(globalFlagPath + "deposit-to-inventory");
        mutuallyExclusiveRegions = config.getBoolean(globalFlagPath + "mutually-exclusive-regions");
        fortuneMultiplier = percentageInDoubleConverter(config.getDouble(globalFlagPath + "fortune-modifier-per-level"));
        luckMultiplier = percentageInDoubleConverter(config.getDouble(globalFlagPath + "luck-modifier-per-level"));
        badLuckMultiplier = percentageInDoubleConverter(config.getDouble(globalFlagPath + "bad-Luck-modifier-per-level"));
        interModifierMultiplicativity = config.getBoolean(globalFlagPath + "inter-modifire-multiplicativity", true);
        intraModifierMultiplicativity = config.getBoolean(globalFlagPath + "intra-modifire-multiplicativity", true);
        publicSound = config.getBoolean(globalFlagPath + "public-reward-sound");

    }

    private double percentageInDoubleConverter(double value) {
        return Math.max(0, 1 + (value / 100));
    }
}
