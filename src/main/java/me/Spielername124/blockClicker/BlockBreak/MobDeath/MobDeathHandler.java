package me.Spielername124.blockClicker.BlockBreak.MobDeath;

import me.Spielername124.blockClicker.BlockBreak.GlobalFlags;
import me.Spielername124.blockClicker.BlockBreak.RewardManagement.Rewards.Reward;
import me.Spielername124.blockClicker.BlockBreak.RewardManagement.Rewards.RewardSound;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import static me.Spielername124.blockClicker.BlockClicker.MOB_ID_KEY;

public class MobDeathHandler {
    public static void handleMobDeath(BlockClicker plugin, GlobalFlags flags, EntityDeathEvent event, MobLootCache cache) {
        //get some event data
        LivingEntity entity = event.getEntity();
        String mobId = entity.getPersistentDataContainer().get(MOB_ID_KEY, PersistentDataType.STRING);
        Location location = entity.getLocation();
        Player killingPlayer = entity.getKiller();
        ItemStack weapon = (killingPlayer != null) ? killingPlayer.getInventory().getItemInMainHand() : null;

        RewardSound sound = new RewardSound(plugin);

        if (!cache.getAllowsNaturalDrops(mobId)){
            event.getDrops().clear();
        }

        for (Reward reward : cache.getRewardList(mobId)){
            // We pass null for the block because mobs are not blocks!
            reward.rollAndExecute(killingPlayer, location, flags, sound, weapon, location.getBlock());
        }

    }
}
