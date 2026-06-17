package BlockBreak.Rewards;

import BlockBreak.GlobalFlags;
import BlockBreak.Rewards.ContainerSpawn.ContainerDrop;
import BlockBreak.Rewards.Effects.Effects;
import BlockBreak.Rewards.GuaranteedReward.GuaranteedReward;
import BlockBreak.Rewards.ItemDrop.ItemDrop;
import BlockBreak.Rewards.MobSpawn.MobSpawn;
import BlockBreak.Rewards.RewardsHelper.Chance;
import BlockBreak.Rewards.RewardsHelper.ExecuteSingleReward;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import java.util.Map;

public class HandleRewards {
    public static void handleGroupDrops(BlockClicker plugin, FileConfiguration config, GlobalFlags flags, Player player, Block block, Location location, ItemStack toolUsed, String parentGroup, String zoneGroup){
        String brokenBlockName = block.getType().name();
        String path = zoneGroup+ "." + parentGroup + "." + brokenBlockName;

        //returns if the block has no specified rewards
        if (!config.contains(path)) {
            return;
        }

        //creates a rewardSound to play a sound in case of a reward
        RewardSound sound = new RewardSound(plugin);

        List<Map<?, ?>> possibleRewards = config.getMapList(path);

        // Iterating through every possible reward for the broken block
        for(Map<?, ?> rewardData : possibleRewards){

            // Retrieves the chance and skips if the drop roll fails
            Number chanceNr = (Number) rewardData.get("chance");
            double chance = chanceNr != null ? chanceNr.doubleValue() : 100;
            boolean isLuckDependent = Boolean.TRUE.equals(rewardData.get("luck-dependence"));
            if(!Chance.performDropRoll(flags, chance, toolUsed, player, isLuckDependent)) continue;

            ExecuteSingleReward.executeReward(plugin, config, sound, rewardData, flags, player, block, location, toolUsed);

        }
        sound.PlaySound(flags, player, location);
    }
}
