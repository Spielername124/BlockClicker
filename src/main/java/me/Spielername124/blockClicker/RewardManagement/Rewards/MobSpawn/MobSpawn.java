package me.Spielername124.blockClicker.RewardManagement.Rewards.MobSpawn;

import me.Spielername124.blockClicker.EventWideFlags;
import me.Spielername124.blockClicker.GlobalFlags;
import me.Spielername124.blockClicker.RewardManagement.Rewards.MobSpawn.Mobs.MobCreator;
import me.Spielername124.blockClicker.RewardManagement.Rewards.MobSpawn.Mobs.SpawnableMob;
import me.Spielername124.blockClicker.RewardManagement.Rewards.Reward;
import me.Spielername124.blockClicker.RewardManagement.Rewards.RewardSoundAndParticle;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class MobSpawn extends Reward {
    private final String arenaType;
    private final SpawnableMob mob;

    public MobSpawn(BlockClicker plugin, FileConfiguration config, Map<?, ?> rewardData) {
        super(plugin, config, rewardData);

        mob = MobCreator.createMob(plugin, super.flags, rewardData);

        arenaType = (String) rewardData.get("arena");

    }

    @Override
    protected void execute(Player player, Location location, GlobalFlags flags, RewardSoundAndParticle sound, ItemStack toolUsed, Block block, EventWideFlags eventWideFlags){
        //spawn the mob
        mob.spawn(plugin, flags, location ,eventWideFlags);

        //Build the arena if specified
        if(arenaType !=null){
            //sets the y value on the minimum of the player or the spawning mob
            int y = player!= null ? Math.min(player.getLocation().getBlockY(), location.getBlockY()): location.getBlockY();
            Arena.buildArena(plugin, location, y, arenaType);
        }


    }
}
