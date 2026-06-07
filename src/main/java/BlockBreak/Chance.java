package BlockBreak;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class Chance {
    public static boolean performDropRoll(GlobalFlags flags, double baseChance, ItemStack toolUsed){

        double totalChance = calculatePostModifierChance(baseChance, flags, toolUsed);

        double randomRoll = ThreadLocalRandom.current().nextDouble(100.0);
        return randomRoll <= baseChance;
    }

    private static double calculatePostModifierChance(double baseChance, GlobalFlags flags, ItemStack toolUsed){
        int fortuneLevel = toolUsed.getEnchantmentLevel(Enchantment.FORTUNE);

        //returns an on 100% capped total chance
        return Math.min(100, baseChance * Math.pow(flags.fortuneMultiplier,fortuneLevel));
    }
}
