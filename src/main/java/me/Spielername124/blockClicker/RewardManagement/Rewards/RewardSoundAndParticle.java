package me.Spielername124.blockClicker.RewardManagement.Rewards;

import com.destroystokyo.paper.ParticleBuilder;
import me.Spielername124.blockClicker.GlobalFlags;
import me.Spielername124.blockClicker.BlockClicker;
import net.kyori.adventure.sound.Sound;
import org.bukkit.Location;

import org.bukkit.World;
import org.bukkit.entity.Player;

public class RewardSoundAndParticle {
    BlockClicker plugin;
    private Sound sound;
    private int currentSoundPriority =-1;

    private Location particleLocation;
    private int currentParticlePriority;
    private ParticleBuilder particle;
    private boolean particleOnPlayer;


    public RewardSoundAndParticle(BlockClicker plugin){
        this.plugin = plugin;
    }

    public void setSound(Sound newSound, int priority){
        if(newSound == null) return;
        if(priority <= currentSoundPriority) return;

        this.sound = newSound;
        this.currentSoundPriority = priority;
    }

    public void setParticle(ParticleBuilder newParticle, int priority, boolean onPlayer){
        if(newParticle == null) return;
        if(priority <= currentSoundPriority) return;

        this.particle = newParticle;
        this.currentParticlePriority = priority;
        particleOnPlayer = onPlayer;
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

    public void PlayParticle(Player player, Location BlockLocation){
        //gets the location
        Location location = BlockLocation;
        if(particleOnPlayer){
            if(player == null) return;
            location = player.getLocation();
        }

        //spawns the particle
        particle.location(location).spawn();

    }

}
