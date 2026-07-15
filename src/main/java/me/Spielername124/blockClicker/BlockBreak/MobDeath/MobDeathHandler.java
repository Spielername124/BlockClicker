package me.Spielername124.blockClicker.BlockBreak.MobDeath;

import me.Spielername124.blockClicker.BlockBreak.GlobalFlags;
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
    public static void handleMobDeath(BlockClicker plugin, EntityDeathEvent event, MobLootCache cache) {

        LivingEntity entity = event.getEntity();
        String mobId = entity.getPersistentDataContainer().get(MOB_ID_KEY, PersistentDataType.STRING);
        Location location = entity.getLocation();

        cache.getRewardList(mobId).getFirst().getItemStack();

    }
}
