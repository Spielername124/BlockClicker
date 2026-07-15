package me.Spielername124.blockClicker.BlockBreak;

import org.bukkit.configuration.file.FileConfiguration;

public class GlobalFlags {
    public boolean mutuallyExclusiveRegions =false;
    public boolean mutuallyExclusiveTools = false;
    public boolean depositToInventory = false;
    public double fortuneMultiplier = 1;
    public double luckMultiplier = 1;
    public double badLuckMultiplier = 1;
    public boolean interModifierMultiplicativity;
    public boolean intraModifierMultiplicativity;
    public boolean publicSound;
    public int recursionDepth;

    //tracks if already a container was rewarded --> if so prevents another one from spawning
    public boolean containerHasBeenPlaced = false;

    public GlobalFlags(FileConfiguration config){
     update(config);
    }

    public void update(FileConfiguration config){
        String globalFlagPath = "global-flags.";
        depositToInventory = config.getBoolean(globalFlagPath + "deposit-to-inventory");
        mutuallyExclusiveRegions = config.getBoolean(globalFlagPath + "mutually-exclusive-regions");
        mutuallyExclusiveTools = config.getBoolean(globalFlagPath + "mutually-exclusive-tools");
        fortuneMultiplier = percentageInDoubleConverter(config.getDouble(globalFlagPath + "fortune-modifier-per-level"));
        luckMultiplier = percentageInDoubleConverter(config.getDouble(globalFlagPath + "luck-modifier-per-level"));
        badLuckMultiplier = percentageInDoubleConverter(config.getDouble(globalFlagPath + "bad-Luck-modifier-per-level"));
        interModifierMultiplicativity = config.getBoolean(globalFlagPath + "inter-modifire-multiplicativity", true);
        intraModifierMultiplicativity = config.getBoolean(globalFlagPath + "intra-modifire-multiplicativity", true);
        publicSound = config.getBoolean(globalFlagPath + "public-reward-sound");
        recursionDepth = config. getInt(globalFlagPath + "recursion-depth", 5);
    }

    private double percentageInDoubleConverter(double value) {
        return Math.max(0, 1 + (value / 100));
    }
}
