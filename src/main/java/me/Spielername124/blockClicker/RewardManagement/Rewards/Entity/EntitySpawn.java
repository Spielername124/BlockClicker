package me.Spielername124.blockClicker.RewardManagement.Rewards.Entity;

import me.Spielername124.blockClicker.BlockClicker;
import me.Spielername124.blockClicker.EventWideFlags;
import me.Spielername124.blockClicker.GlobalFlags;
import me.Spielername124.blockClicker.RewardManagement.Rewards.MobSpawn.Arena;
import me.Spielername124.blockClicker.RewardManagement.Rewards.MobSpawn.Mobs.MobCreator;
import me.Spielername124.blockClicker.RewardManagement.Rewards.MobSpawn.Mobs.SpawnableMob;
import me.Spielername124.blockClicker.RewardManagement.Rewards.Reward;
import me.Spielername124.blockClicker.RewardManagement.Rewards.RewardSoundAndParticle;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import static me.Spielername124.blockClicker.Helper.MapParser.*;

import java.util.Map;

public class EntitySpawn extends Reward {
    private EntityType entityType;
    private final boolean onPlayer;
    private final int fuseTicks;
    private final float yield;

    public EntitySpawn(BlockClicker plugin, FileConfiguration config, Map<?, ?> rewardData) {
        super(plugin, config, rewardData);

        String typeStr = getString(rewardData, "entity", null);
        try {
            entityType = EntityType.valueOf(typeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            plugin.getLogger().warning("[BlockClicker] Unknown entity type: " + typeStr);
        }

        onPlayer= getBoolean(rewardData, "on_player", false);
        //defaults to Minecraft's default value
        fuseTicks = getInt(rewardData, "fuseTicks", -1);
        this.yield = (float) getDouble(rewardData, "yield", 4.0);
    }

    @Override
    protected void execute(Player player, Location location, GlobalFlags flags, RewardSoundAndParticle sound, ItemStack toolUsed, Block block, EventWideFlags eventWideFlags) {
        if (entityType == null) return;

        //chooses the spawn location
        Location spawnLocation = onPlayer ? player.getLocation() : location.clone().add(0.5, 0, 0.5);

        World world = spawnLocation.getWorld();
        if (world != null) {
            Entity spawnedEntity = world.spawnEntity(spawnLocation, entityType);

            if (spawnedEntity instanceof TNTPrimed tnt) {
                if (fuseTicks >= 0) {
                    tnt.setFuseTicks(fuseTicks);
                }
                tnt.setYield(yield);
            }
        }
    }
}

