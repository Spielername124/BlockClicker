package BlockBreak.RewardManagement.Rewards.ItemDrop;

import BlockBreak.GlobalFlags;
import org.bukkit.Location;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class XpDrop {

    public static void performXpDrop(GlobalFlags flags, Player player, Location location, int amount, ItemStack toolUsed) {

        if( amount > 0) {
            if (flags.depositToInventory) player.giveExp(amount);
            else {
                ExperienceOrb xpDrop = location.getWorld().spawn(location, ExperienceOrb.class);
                xpDrop.setExperience(amount);
            }
        }
    }
}
