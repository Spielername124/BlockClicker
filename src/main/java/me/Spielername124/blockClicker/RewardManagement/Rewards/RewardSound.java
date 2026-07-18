package me.Spielername124.blockClicker.RewardManagement.Rewards;

import me.Spielername124.blockClicker.GlobalFlags;
import me.Spielername124.blockClicker.BlockClicker;
import net.kyori.adventure.sound.Sound;
import org.bukkit.Location;

import org.bukkit.World;
import org.bukkit.entity.Player;

public class RewardSound {
    BlockClicker plugin;
    private Sound sound;
    private int currentPriority =-1;

    public RewardSound(BlockClicker plugin){
        this.plugin = plugin;
    }

    public void setSound(Sound newSound, int priority){
        if(newSound == null) return;
        if(priority <= currentPriority) return;

        this.sound = newSound;
        this.currentPriority = priority;
    }


    public void PlaySound(GlobalFlags flags, Player player, Location location){
        if (sound!= null){
            //play the sound if possible
            if(flags.publicSound){
                World world = player.getWorld();
                world.playSound(this.sound, location.getX(), location.getY(), location.getZ());
            }
            else if (player != null){
                player.playSound(sound);
            }

        }
    }

}
