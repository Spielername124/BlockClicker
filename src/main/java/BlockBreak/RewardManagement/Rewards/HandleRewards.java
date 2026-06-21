package BlockBreak.RewardManagement.Rewards;

import BlockBreak.GlobalFlags;
import BlockBreak.RewardManagement.RewardCache;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.List;

public class HandleRewards {
    public static void handleGroupDrops(BlockClicker plugin, RewardCache cache, GlobalFlags flags, Player player, Block block, Location location, ItemStack toolUsed, String parentGroup, String zoneGroup) {

        //creates a rewardSound to play a sound in case of a reward
        RewardSound sound = new RewardSound(plugin);

        List<Reward> rewardList = cache.getRewardList(zoneGroup, parentGroup, block.getType());

        // Iterating through every possible reward for this zone group and roll and Execute it
        for(Reward possibleReward : rewardList){
            possibleReward.rollAndExecute(player, location, flags, sound, toolUsed, block);
        }

        sound.PlaySound(flags, player, location);
    }
}
