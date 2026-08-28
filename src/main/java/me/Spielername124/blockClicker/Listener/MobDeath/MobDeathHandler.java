package me.Spielername124.blockClicker.Listener.MobDeath;

import me.Spielername124.blockClicker.EventWideFlags;
import me.Spielername124.blockClicker.GlobalFlags;
import me.Spielername124.blockClicker.RewardManagement.Rewards.Reward;
import me.Spielername124.blockClicker.RewardManagement.Rewards.RewardSoundAndParticle;
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
        //we use the block where the mobs under body part was
        Location location = new Location(entity.getWorld(), entity.getLocation().getX(), entity.getLocation().getY()+1, entity.getLocation().getZ());
        Player killingPlayer = entity.getKiller();
        ItemStack weapon = (killingPlayer != null) ? killingPlayer.getInventory().getItemInMainHand() : null;



        RewardSoundAndParticle sound = new RewardSoundAndParticle(plugin);
        EventWideFlags eventWideFlags = new EventWideFlags();

        if (!cache.getAllowsNaturalDrops(mobId)){
            event.getDrops().clear();
        }

        for (Reward reward : cache.getRewardList(mobId)){
            reward.rollAndExecute(killingPlayer, location, sound, weapon, null, eventWideFlags);
        }
        sound.PlaySound(flags, killingPlayer, location);
        sound.PlayParticle(killingPlayer, location);

    }
}
