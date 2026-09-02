package me.Spielername124.blockClicker.RewardManagement.Rewards.MobSpawn.Mobs;

import me.Spielername124.blockClicker.BlockClicker;
import me.Spielername124.blockClicker.EventWideFlags;
import me.Spielername124.blockClicker.GlobalFlags;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;

public abstract class SpawnableMob {
    private final EntityType type;
    private final String customMobId;
    private final boolean customHP;
    private final double hp;
    private final String arenaType;
    private final Number ttl;
    private final String displayName;

    public SpawnableMob(BlockClicker plugin, Map<?, ?> rewardData, EntityType type) {

        this.type = type;

        customMobId = (String) rewardData.get("mob-id");

        Number hpRaw = (Number) rewardData.get("health");
        customHP = hpRaw != null;
        hp = customHP ? hpRaw.doubleValue() : 0;

        arenaType = (String) rewardData.get("arena");

        ttl = (Number) rewardData.get("ttl");

        displayName = (String) rewardData.get("display-name");
    }


    public LivingEntity spawn(BlockClicker plugin, GlobalFlags flags, Location location, EventWideFlags eventWideFlags){

        if (type ==  null || !type.isAlive()) {
            plugin.getLogger().warning("Tried to spawn a mob with a invalid Type");
            return null;
        }
        // make mob spawning mutually exclusive with container spawns
        if(eventWideFlags.blocksHaveBeenManipulated)
            return null;
        eventWideFlags.blocksHaveBeenManipulated = true;

        //center the mob spawn so that mobs don't spawn on the edge of the block
        Location spawnLoc = location.clone();
        spawnLoc.add(0.5, 0.1, 0.5);


        LivingEntity spawnedMob = (LivingEntity) spawnLoc.getWorld().spawnEntity(spawnLoc, type);

        //sets the custom Name if specified
        if (displayName != null && !displayName.isEmpty()) {
            Component formattedName = LegacyComponentSerializer.legacyAmpersand().deserialize(displayName);
            spawnedMob.customName(formattedName);
            spawnedMob.setCustomNameVisible(true);
        }

        //sets the custom HP if specified
        if(customHP) {
            AttributeInstance mobHpAttribute = spawnedMob.getAttribute(Attribute.MAX_HEALTH);
            if (mobHpAttribute != null) {
                mobHpAttribute.setBaseValue(hp);
                spawnedMob.setHealth(hp);
            }
        }


        // adds the mobId if one exists
        if (customMobId != null && !customMobId.isEmpty()) {
            spawnedMob.getPersistentDataContainer().set(
                    BlockClicker.MOB_ID_KEY,
                    PersistentDataType.STRING,
                    customMobId
            );
        }
        // gets the mob height
        int requiredHeightBlocks = (int) Math.ceil(spawnedMob.getHeight());
        //goes through all the mobs blocks (in height) and makes them to air to prevent suffocation
        for (int i = 1; i < requiredHeightBlocks; i++) {
            Location checkLoc = location.clone().add(0, i, 0);
            Block checkBlock = checkLoc.getBlock();

            // Only clear the block if it is solid/collidable and region permissions allow it
            if (!checkBlock.isPassable() && plugin.zoneCache.isAllowedToBeManipulated(checkLoc)) {
                checkBlock.setType(Material.AIR);
            }
        }

        // removes the entity after its ttl if a ttl is specified
        if(ttl != null && ttl.doubleValue()>0) {
            int ttlTicks = (int) ttl.doubleValue() * 20;
            spawnedMob.getScheduler().runDelayed(plugin, (task) -> {
                if (spawnedMob.isValid()) {
                    spawnedMob.remove();
                }
            }, null, ttlTicks);
        }

        return spawnedMob;
    }
}
