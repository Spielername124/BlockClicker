package BlockBreak.RewardManagement.Rewards.Effects;

import BlockBreak.GlobalFlags;
import BlockBreak.RewardManagement.Rewards.Reward;
import BlockBreak.RewardManagement.Rewards.RewardSound;
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

    public EffectReward(BlockClicker plugin, FileConfiguration config,  Map<?, ?> rewardData) {
        super(plugin, config, rewardData);
    }

    @Override
    protected void execute (Player player, Location location, GlobalFlags flags, ItemStack toolUsed, Block block){
        //Get the effect type and convert it to a actual effect Type
        String effectName = (String) rewardData.get("effect");
        if(effectName==null) return;
        PotionEffectType effectType = PotionEffectType.getByName(effectName.toUpperCase());
        if(effectType== null) return;

        Number duration = (Number) rewardData.get("duration-duration");
        int durationTicks = duration != null ? (int) duration *20 : 200;

        Number levelNr = (Number) rewardData.get("level");
        int level = levelNr!=null ? (int) levelNr + 1 : 1;


            PotionEffect effect = new PotionEffect(effectType, durationTicks, level, true, false, true);
            player.addPotionEffect(effect);
    }
}
