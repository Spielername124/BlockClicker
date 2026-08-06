package me.Spielername124.blockClicker.RewardManagement.Rewards.MobSpawn;

import me.Spielername124.blockClicker.GlobalFlags;
import me.Spielername124.blockClicker.RewardManagement.Rewards.MobSpawn.Mobs.MobCreator;
import me.Spielername124.blockClicker.RewardManagement.Rewards.MobSpawn.Mobs.SpawnableMob;
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
    private final String arenaType;
    private final SpawnableMob mob;

    public MobSpawn(BlockClicker plugin, FileConfiguration config, Map<?, ?> rewardData) {
        super(plugin, config, rewardData);

        mob = MobCreator.createMob(plugin, rewardData);

        arenaType = (String) rewardData.get("arena");

    }

    @Override
    protected void execute(Player player, Location location, GlobalFlags flags, RewardSound sound, ItemStack toolUsed, Block block){
        //spawn the mob
        mob.spawn(plugin, flags, location);

        //Build the arena if specified
        if(arenaType !=null){
            //sets the y value on the minimum of the player or the spawning mob
            int y = player!= null ? Math.min(player.getLocation().getBlockY(), location.getBlockY()): location.getBlockY();
            Arena.buildArena(plugin, location, y, arenaType);
        }


    }
}
