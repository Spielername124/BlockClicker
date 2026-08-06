package me.Spielername124.blockClicker.RewardManagement.Rewards.Effects;

import me.Spielername124.blockClicker.GlobalFlags;
import me.Spielername124.blockClicker.RewardManagement.Rewards.Reward;
import me.Spielername124.blockClicker.RewardManagement.Rewards.RewardSound;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;

public class EffectReward extends Reward {

    private final PotionEffectType effectType;
    private final int durationTicks;
    private final int level;

    public EffectReward(BlockClicker plugin, FileConfiguration config,  Map<?, ?> rewardData) {
        super(plugin, config, rewardData);

        String effectName = (String) rewardData.get("effect");
        if(effectName!=null) {
            NamespacedKey key = NamespacedKey.fromString(effectName.toLowerCase());
            effectType = key != null ? Registry.EFFECT.get(key) : null;
        }
        else effectType=null;

        Number duration = (Number) rewardData.get("effect-duration");
        durationTicks = duration != null ? (int) duration *20 : 200;

        Number levelNr = (Number) rewardData.get("level");
        level = levelNr!=null ? levelNr.intValue() - 1 : 0;


    }

    @Override
    protected void execute(Player player, Location location, GlobalFlags flags, RewardSound sound, ItemStack toolUsed, Block block){
        if(effectType== null) return;

        if(player == null) return;

        PotionEffect effect = new PotionEffect(effectType, durationTicks, level, true, false, true);
        player.addPotionEffect(effect);
    }
}
