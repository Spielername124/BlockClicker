package me.Spielername124.blockClicker.BlockBreak.RewardManagement.Rewards.Effects;

import me.Spielername124.blockClicker.BlockBreak.GlobalFlags;
import me.Spielername124.blockClicker.BlockBreak.RewardManagement.Rewards.Reward;
import me.Spielername124.blockClicker.BlockBreak.RewardManagement.Rewards.RewardSound;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.Location;
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
            effectType = PotionEffectType.getByName(effectName.toUpperCase());
        }
        else effectType=null;

        Number duration = (Number) rewardData.get("duration-duration");
        durationTicks = duration != null ? (int) duration *20 : 200;

        Number levelNr = (Number) rewardData.get("level");
        level = levelNr!=null ? (int) levelNr + 1 : 1;


    }

    @Override
    protected void execute(Player player, Location location, GlobalFlags flags, RewardSound sound, ItemStack toolUsed, Block block){
        if(effectType== null) return;

        if(player == null) return;

        PotionEffect effect = new PotionEffect(effectType, durationTicks, level, true, false, true);
        player.addPotionEffect(effect);
    }
}
