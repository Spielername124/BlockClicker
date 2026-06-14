package BlockBreak.Rewards.MobSpawn;

import BlockBreak.GlobalFlags;
import BlockBreak.Rewards.RewardSound;
import BlockBreak.Rewards.RewardsHelper.Chance;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class MobSpawn {
    public static void rollMobSpawn(BlockClicker plugin, RewardSound sound, Map<?, ?> rewardData, GlobalFlags flags, Player player, Location location, ItemStack toolUsed){

        //polls mob type and returns if none is specified
        String mobType = (String) rewardData.get("mob");
        if(mobType==null) return;

        //gets the odds
        Number chanceNr = (Number) rewardData.get("chance");
        double chance = chanceNr != null ? chanceNr.doubleValue() : 100;

        boolean isLuckDependent = Boolean.TRUE.equals(rewardData.get("luck-dependence"));

        if(Chance.performDropRoll(flags, chance, toolUsed, player, isLuckDependent)){
            try{
                //get the entity type and spawn the entity
                EntityType type = EntityType.valueOf(mobType);
                Entity entity = location.getWorld().spawnEntity(location, type);

                //set the sound if existing
                sound.setSound(rewardData);
            }
            catch (IllegalArgumentException e) {
                plugin.getLogger().warning("Invalid EntityType '" + mobType);
            }

        }

    }
}
