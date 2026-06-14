package BlockBreak.Rewards.RewardsHelper;

import BlockBreak.GlobalFlags;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.ThreadLocalRandom;

public class Chance {
    public static boolean performDropRoll(GlobalFlags flags, double baseChance, ItemStack toolUsed, Player player){

        double totalChance = calculatePostModifierChance(baseChance, flags, toolUsed, player);

        double randomRoll = ThreadLocalRandom.current().nextDouble(100.0);
        return randomRoll <= totalChance;
    }

    private static double calculatePostModifierChance(double baseChance, GlobalFlags flags, ItemStack toolUsed, Player player){
        int fortuneLevel = toolUsed.getEnchantmentLevel(Enchantment.FORTUNE);

        //get the luck level
        int luckLevel=0;
        PotionEffect luckEffect = player.getPotionEffect(PotionEffectType.LUCK);
        if(luckEffect!=null)
            luckLevel= luckEffect.getAmplifier()+1;

        //unluckLevel
        int unluckLevel = 0;
        PotionEffect unluckEffect = player.getPotionEffect(PotionEffectType.UNLUCK);
        if(unluckEffect!= null)
            unluckLevel = unluckEffect.getAmplifier()+1;


        //calculates and returns the total chance per type of modifier, depending on if they are multiplicative or additive
        double fortuneModifier = flags.intraModifierMultiplicativity ? Math.pow(flags.fortuneMultiplier,fortuneLevel) : flags.fortuneMultiplier * fortuneLevel ;
        double luckModifier = flags.intraModifierMultiplicativity ? Math.pow(flags.luckMultiplier, luckLevel) : flags.luckMultiplier * luckLevel;
        double badLuckModifier = flags.intraModifierMultiplicativity ? Math.pow(flags.badLuckMultiplier, unluckLevel) : flags.badLuckMultiplier * unluckLevel;

        //calculates the total modifier depending on if the submodifier are multiplicative or additive
        double totalModifier= flags.interModifierMultiplicativity ? fortuneModifier * luckModifier * badLuckModifier : fortuneModifier + luckModifier + badLuckModifier;

        //returns an on 0% and 100% capped total chance
        return Math.clamp(baseChance * totalModifier, 0, 100);
    }
}
