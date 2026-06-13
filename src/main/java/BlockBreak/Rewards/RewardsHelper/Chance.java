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
        return randomRoll <= baseChance;
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


        //calculates and returns the total chance
        double fortuneModifier = Math.pow(flags.fortuneMultiplier,fortuneLevel);
        double luckModifier = Math.pow(flags.luckMultiplier, luckLevel);
        double badLuckModifier = Math.pow(flags.badLuckMultiplier, unluckLevel);


        //returns an on 100% capped total chance
        return Math.min(100, baseChance * fortuneModifier * luckModifier * badLuckModifier);
    }
}
