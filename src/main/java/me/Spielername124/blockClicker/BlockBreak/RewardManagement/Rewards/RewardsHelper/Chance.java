package me.Spielername124.blockClicker.BlockBreak.RewardManagement.Rewards.RewardsHelper;

import me.Spielername124.blockClicker.BlockBreak.GlobalFlags;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.ThreadLocalRandom;

public class Chance {

    public static boolean performDropRoll(GlobalFlags flags, double baseChance, ItemStack toolUsed, Player player, Block block, boolean  isLuckLuckDependent){

        double totalChance = isLuckLuckDependent ? calculatePostModifierChance(baseChance, flags, toolUsed, player, block): baseChance;

        double randomRoll = ThreadLocalRandom.current().nextDouble(100.0);
        return randomRoll <= totalChance;
    }

    private static double calculatePostModifierChance(double baseChance, GlobalFlags flags, ItemStack toolUsed, Player player, Block block){
        int luckLevel = 0;
        int unluckLevel = 0;
        int fortuneLevel = 0;
        int lootingLevel = 0;

        //check the player for modifiers if he is not null
        if(player!=null) {
            //get the luck level
            PotionEffect luckEffect = player.getPotionEffect(PotionEffectType.LUCK);
            if (luckEffect != null)
                luckLevel = luckEffect.getAmplifier() + 1;
            //unluckLevel
            PotionEffect unluckEffect = player.getPotionEffect(PotionEffectType.UNLUCK);
            if (unluckEffect != null)
                unluckLevel = unluckEffect.getAmplifier() + 1;
        }
        //check the tool used if it isn't null
        if(toolUsed!=null) {
            //if the block is set to null, we know that this chance roll was performed by a mob kill --> we will check the looting level of the item it was killed with, instead of the fortune level
            if (block != null) {
                fortuneLevel = toolUsed.getEnchantmentLevel(Enchantment.FORTUNE);
            }
            else {
                lootingLevel = toolUsed.getEnchantmentLevel(Enchantment.LOOTING);
            }
        }




        //calculates and returns the total chance per type of modifier, depending on if they are multiplicative or additive
        double fortuneModifier = flags.intraModifierMultiplicativity ? Math.pow(flags.fortuneMultiplier,fortuneLevel) : flags.fortuneMultiplier * fortuneLevel;
        double lootingModifier = flags.intraModifierMultiplicativity ? Math.pow(flags.lootingMultiplier,lootingLevel) : flags.lootingMultiplier * lootingLevel;

        double luckModifier = flags.intraModifierMultiplicativity ? Math.pow(flags.luckMultiplier, luckLevel) : flags.luckMultiplier * luckLevel;
        double badLuckModifier = flags.intraModifierMultiplicativity ? Math.pow(flags.badLuckMultiplier, unluckLevel) : flags.badLuckMultiplier * unluckLevel;

        //calculates the total modifier depending on if the submodifier are multiplicative or additive
        double totalModifier= flags.interModifierMultiplicativity ? fortuneModifier * lootingModifier * luckModifier * badLuckModifier : fortuneModifier + lootingModifier + luckModifier + badLuckModifier;

        //returns an on 0% and 100% capped total chance
        return Math.clamp(baseChance * totalModifier, 0, 100);
    }
}
