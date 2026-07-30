package me.Spielername124.blockClicker.RewardManagement.Rewards.MobSpawn;

import me.Spielername124.blockClicker.GlobalFlags;
import me.Spielername124.blockClicker.RewardManagement.Rewards.Reward;
import me.Spielername124.blockClicker.RewardManagement.Rewards.RewardSound;
import me.Spielername124.blockClicker.BlockClicker;
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
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;

public class MobSpawn extends Reward {

    private final EntityType type;
    private final String customMobId;
    private final boolean customHP;
    private final double hp;
    private final String arenaType;
    private final Number ttl;
    private final String displayName;


    public MobSpawn(BlockClicker plugin, FileConfiguration config, Map<?, ?> rewardData) {
        super(plugin, config, rewardData);

        customMobId = (String) rewardData.get("mob-id");

        Number hpRaw = (Number) rewardData.get("health");
        customHP = hpRaw != null;
        hp = customHP ? hpRaw.doubleValue() : 0;

        arenaType = (String) rewardData.get("arena");

        ttl = (Number) rewardData.get("ttl");

        displayName = (String) rewardData.get("display-name");

        String mobType = (String) rewardData.get("mob");
        if(mobType==null){
            type = null;
            return;
        }

        EntityType localType = null;
        try{
            //get the entity type
            localType = EntityType.valueOf(mobType);

        }
        catch (IllegalArgumentException e) {
            plugin.getLogger().warning("Invalid EntityType '" + mobType);
        }

        type = localType;

    }

    @Override
    protected void execute(Player player, Location location, GlobalFlags flags, RewardSound sound, ItemStack toolUsed, Block block){

        if (type ==  null || !type.isAlive()) {
            plugin.getLogger().warning("Tried to spawn a mob with a invalid Type");
            return;
        }
        // make mob spawning mutually exclusive with container spawns
        if(flags.containerHasBeenPlaced)
            return;
        flags.containerHasBeenPlaced = true;

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

        Location locationOnYPlus1 = new Location(location.getWorld(),  location.getX(), location.getY()+1, location.getZ());
        if(plugin.zoneCache.isAllowedToBeManipulated(locationOnYPlus1)){
            Block blockOnZPlus1 = locationOnYPlus1.getBlock();
            blockOnZPlus1.setType(Material.AIR);
        }

        //Build the arena if specified
        if(arenaType !=null){
            //sets the y value on the minimum of the player or the spawning mob
            int y = player!= null ? Math.min(player.getLocation().getBlockY(), location.getBlockY()): location.getBlockY();
            Arena.buildArena(plugin, location, y, arenaType);
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
    }
}
