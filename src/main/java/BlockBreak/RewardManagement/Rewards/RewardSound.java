package BlockBreak.RewardManagement.Rewards;

import BlockBreak.GlobalFlags;
import me.Spielername124.blockClicker.BlockClicker;
import net.kyori.adventure.key.InvalidKeyException;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.Location;

import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Map;

public class RewardSound {
    BlockClicker plugin;
    private Sound sound;
    private int currentPriority =-1;

    public RewardSound(BlockClicker plugin){
        this.plugin = plugin;
    }

    public void setSound(Map<?, ?> rewardData){
        //get sound
        String soundSt = (String) rewardData.get("sound");
        if(soundSt== null) return;
        soundSt.trim();

        //get priority, return if the new sound has a lower priority than the current one
        Number priorityNr = (Number) rewardData.get("sound-priority");
        int priority = priorityNr != null ? priorityNr.intValue() : 0;
        if(priority <= currentPriority) return;
        try {
            //overwrite the sound and priority if possible
            Key soundKey = Key.key(soundSt.toLowerCase());
            this.sound = Sound.sound(soundKey, Sound.Source.MASTER, 1.0f, 1.0f);
            this.currentPriority = priority;

        } catch (InvalidKeyException e) {
            // throw a error if the key is invalid
            plugin.getLogger().severe("The sound '" + soundSt + "' in your config has invalid characters! Must be lowercase only.");
        }

    }


    public void PlaySound(GlobalFlags flags, Player player, Location location){
        if (sound!= null){
            //play the sound if possible
            if(flags.publicSound){
                World world = player.getWorld();
                world.playSound(this.sound, location.getX(), location.getY(), location.getZ());
            }
            else{
                player.playSound(sound);
            }

        }
    }

}
