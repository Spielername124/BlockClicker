package BlockBreak.Rewards.Effects;

import BlockBreak.GlobalFlags;
import BlockBreak.Rewards.RewardsHelper.Chance;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;

public class Effects {
    public static void GiveEffect(Map<?, ?> rewardData, GlobalFlags flags, Player player, ItemStack toolUsed){

        //Get the effect type and convert it to a actual effect Type
        String effectName = (String) rewardData.get("effect");
        if(effectName==null)return;
        PotionEffectType effectType = PotionEffectType.getByName(effectName.toUpperCase());
        if(effectType== null) return;

        Number chanceNr = (Number) rewardData.get("chance");
        double chance = chanceNr != null ? chanceNr.doubleValue() : 100;

        Number duration = (Number) rewardData.get("duration-duration");
        int durationTicks = duration != null ? (int) duration *20 : 200;

        Number levelNr = (Number) rewardData.get("level");
        int level = levelNr!=null ? (int) levelNr + 1 : 1;

        if(Chance.performDropRoll(flags, chance, toolUsed, player)) {
            PotionEffect effect = new PotionEffect(effectType, durationTicks, level, true, false, true);
            player.addPotionEffect(effect);
        }

    }
}
