package BlockBreak.Rewards.ItemDrop;

import BlockBreak.GlobalFlags;
import BlockBreak.Rewards.RewardsHelper.Chance;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.Location;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class XpDrop {

    public static void performXpDrop(GlobalFlags flags, Player player, Location location, int amount, double chance, ItemStack toolUsed) {

        if( amount > 0 && Chance.performDropRoll(flags, chance, toolUsed, player) ) {
            if (flags.depositToInventory) player.giveExp(amount);
            else {
                ExperienceOrb xpDrop = location.getWorld().spawn(location, ExperienceOrb.class);
                xpDrop.setExperience(amount);
            }
        }
    }
}
