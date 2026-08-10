package me.Spielername124.blockClicker;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.Map;

public class GlobalFlags {
    public boolean mutuallyExclusiveRegions =false;
    public boolean mutuallyExclusiveTools = false;
    public boolean depositToInventory = false;
    public double fortuneMultiplier = 1;
    public double lootingMultiplier = 1;
    public double luckMultiplier = 1;
    public double badLuckMultiplier = 1;
    public boolean interModifierMultiplicativity;
    public boolean intraModifierMultiplicativity;
    public boolean publicSound;
    public int recursionDepth;
    public int maxTradeUsage;


    public GlobalFlags (GlobalFlags other, Map<?, ?>  localOverwrite) {
        depositToInventory = getBooleanOverwrite(localOverwrite.get("deposit-to-inventory"), other.depositToInventory);
        mutuallyExclusiveRegions = getBooleanOverwrite(localOverwrite.get("mutually-exclusive-regions"), other.mutuallyExclusiveRegions);
        mutuallyExclusiveTools = getBooleanOverwrite(localOverwrite.get("mutually-exclusive-tools"), other.mutuallyExclusiveTools);

        fortuneMultiplier = getDoubleOverwrite(localOverwrite.get("mutually-exclusive-tools"), other.fortuneMultiplier);
        lootingMultiplier = getDoubleOverwrite(localOverwrite.get("looting-modifier-per-level"), other.lootingMultiplier);
        luckMultiplier = getDoubleOverwrite(localOverwrite.get("luck-modifier-per-level"), other.luckMultiplier);
        badLuckMultiplier = getDoubleOverwrite(localOverwrite.get("bad-Luck-modifier-per-level"), other.badLuckMultiplier);

        interModifierMultiplicativity = getBooleanOverwrite(localOverwrite.get("inter-modifire-multiplicativity"), other.interModifierMultiplicativity);
        intraModifierMultiplicativity = getBooleanOverwrite(localOverwrite.get("intra-modifire-multiplicativity"), other.intraModifierMultiplicativity);
        publicSound = getBooleanOverwrite(localOverwrite.get("public-reward-sound"), other.publicSound);
        recursionDepth = getIntOverwrite (localOverwrite.get("recursion-depth"), other.recursionDepth);
        maxTradeUsage = getIntOverwrite (localOverwrite.get("max-trades"), other.maxTradeUsage);

    }

    //makes a copy of an existing flags instance
    public GlobalFlags (GlobalFlags other) {
        this.mutuallyExclusiveRegions = other.mutuallyExclusiveRegions;
        this.mutuallyExclusiveTools = other.mutuallyExclusiveTools;
        this.depositToInventory = other.depositToInventory;
        this.fortuneMultiplier = other.fortuneMultiplier;
        this.lootingMultiplier = other.lootingMultiplier;
        this.luckMultiplier = other.luckMultiplier;
        this.badLuckMultiplier = other.badLuckMultiplier;
        this.interModifierMultiplicativity = other.interModifierMultiplicativity;
        this.intraModifierMultiplicativity = other.intraModifierMultiplicativity;
        this.publicSound = other.publicSound;
        this.recursionDepth = other.recursionDepth;
        this.maxTradeUsage = other.maxTradeUsage;
    }

    public GlobalFlags(FileConfiguration config){
     update(config);
    }

    public void update(FileConfiguration config){
        String globalFlagPath = "global-flags.";
        depositToInventory = config.getBoolean(globalFlagPath + "deposit-to-inventory");
        mutuallyExclusiveRegions = config.getBoolean(globalFlagPath + "mutually-exclusive-regions");
        mutuallyExclusiveTools = config.getBoolean(globalFlagPath + "mutually-exclusive-tools");
        fortuneMultiplier = percentageInDoubleConverter(config.getDouble(globalFlagPath + "fortune-modifier-per-level"));
        lootingMultiplier = percentageInDoubleConverter(config.getDouble(globalFlagPath + "looting-modifier-per-level"));
        luckMultiplier = percentageInDoubleConverter(config.getDouble(globalFlagPath + "luck-modifier-per-level"));
        badLuckMultiplier = percentageInDoubleConverter(config.getDouble(globalFlagPath + "bad-Luck-modifier-per-level"));
        interModifierMultiplicativity = config.getBoolean(globalFlagPath + "inter-modifire-multiplicativity", true);
        intraModifierMultiplicativity = config.getBoolean(globalFlagPath + "intra-modifire-multiplicativity", true);
        publicSound = config.getBoolean(globalFlagPath + "public-reward-sound");
        recursionDepth = config. getInt(globalFlagPath + "recursion-depth", 5);
        maxTradeUsage = config. getInt(globalFlagPath +" max-trade-usage-default", 10);
    }

    private double percentageInDoubleConverter(double value) {
        return Math.max(0, 1 + (value / 100));
    }

    //little helper to only overwrite a flag if the new value is specifically set
    private double getDoubleOverwrite(Object rawData,  double defaultValue) {
        return (rawData instanceof Number number)?
                percentageInDoubleConverter(number.doubleValue()) :
                defaultValue;
    }
    private int getIntOverwrite(Object rawData, int defaultValue) {
        return (rawData instanceof Number number)?
                number.intValue():
                defaultValue;
    }

    private boolean getBooleanOverwrite(Object rawData, boolean defaultValue) {
        return (rawData instanceof Boolean bool)?
                bool :
                defaultValue;
    }
}
