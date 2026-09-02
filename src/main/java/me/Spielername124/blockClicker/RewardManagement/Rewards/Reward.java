package me.Spielername124.blockClicker.RewardManagement.Rewards;

import com.destroystokyo.paper.ParticleBuilder;
import me.Spielername124.blockClicker.EventWideFlags;
import me.Spielername124.blockClicker.GlobalFlags;
import me.Spielername124.blockClicker.RewardManagement.Rewards.RewardsHelper.Chance;
import me.Spielername124.blockClicker.BlockClicker;
import net.kyori.adventure.key.InvalidKeyException;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.Map;
import static me.Spielername124.blockClicker.Helper.MapParser.*;

public abstract class Reward {
    public final FileConfiguration config;
    protected final BlockClicker plugin;
    protected final double chance;
    public final Map<?, ?> rewardData;
    private final Chance.LuckModifierDependence luckModifierDependence;

    private final Sound sound;
    private final int soundPriority;

    private final ParticleBuilder particle;
    private final int particlePriority;
    private final boolean particleOnPlayer;


    protected final GlobalFlags flags;



    public Reward(BlockClicker plugin, FileConfiguration config, Map<?, ?> rewardData) {

        Number chanceNr = (Number) rewardData.get("chance");
        chance = chanceNr != null ? chanceNr.doubleValue() : 100;
        luckModifierDependence = getLuckModifierDependence(rewardData,"luck-modifier-dependence", Chance.LuckModifierDependence.NORMAL);
        this.rewardData = rewardData;
        this.plugin = plugin;
        this.config = config;

        //handle the local flag overwrite
        flags = rewardData.get("local-flags") instanceof Map<?, ?> localFlags ?
                new GlobalFlags(plugin.flags, localFlags):
                plugin.flags;

        //set the sound priority
        Number priorityNr = (Number) rewardData.get("sound-priority");
        this.soundPriority = priorityNr != null ? priorityNr.intValue() : 0;
        //parse the sound
        this.sound = getSoundFromConfig(rewardData);

        particle = getParticleFromConfig(rewardData);
        particlePriority = getInt(rewardData, "particle-priority", 0);
        particleOnPlayer = getBoolean(rewardData, "particle-on-player", false);
    }

    public final void rollAndExecute(Player player, Location location, RewardSoundAndParticle soundAndParticle, ItemStack toolUsed, Block block, EventWideFlags eventWideFlags) {

        //roll if the reward is granted, return if not
        if(!Chance.performDropRoll(flags, chance, toolUsed, player, block, luckModifierDependence)) return;

        execute(player, location, flags, soundAndParticle, toolUsed, block, eventWideFlags);

       soundAndParticle.setSound(this.sound, soundPriority);
       soundAndParticle.setParticle(particle,particlePriority,particleOnPlayer);

    }

    protected abstract void execute(Player player, Location location, GlobalFlags flags, RewardSoundAndParticle sound, ItemStack toolUsed, Block block, EventWideFlags eventWideFlags);

    private Sound getSoundFromConfig(Map<?, ?> rewardData){
        //set the sound data for the reward
        String soundSt = (String) rewardData.get("sound");

        if (soundSt == null || soundSt.isBlank()) {
            return null;
        }
        try {
            Key soundKey = Key.key(soundSt.trim().toLowerCase());
            return Sound.sound(soundKey, Sound.Source.MASTER, 1.0f, 1.0f);
        } catch (InvalidKeyException e) {
            plugin.getLogger().severe("The sound '" + soundSt + "' in your config has invalid characters!");
            return null;
        }


    }

    private ParticleBuilder getParticleFromConfig(Map<?, ?> rewardData){
        if (rewardData==null) return null;

        String particleTypeName = getString(rewardData, "particle",null);
        Particle particleType;

        if (particleTypeName == null) return null;

        try {
            particleType = Particle.valueOf(particleTypeName.toUpperCase());
        } catch (IllegalArgumentException e) {
            //defaults to flame
            particleType = Particle.FLAME;
        }

        ParticleBuilder builder = new ParticleBuilder(particleType)
                .count(getInt(rewardData, "count", 10))
                .extra(getDouble(rewardData, "speed", 0.05));

        if (rewardData.get("offset") instanceof Map<?, ?> offsetMap) {
            builder.offset(
                    getDouble(offsetMap, "x", 0.0),
                    getDouble(offsetMap, "y", 0.0),
                    getDouble(offsetMap, "z", 0.0)
            );
        }
        if (particleType == Particle.DUST) {
            String hexColor = getString(rewardData, "color", "#FFFFFF");
            Color color = parseHexColor(hexColor);
            float size = (float) getDouble(rewardData, "size", 1.0);

            builder.data(new Particle.DustOptions(color, size));
        }

        return builder;
    }

    private Color parseHexColor(String hex) {
        if (hex == null || hex.isBlank()) return Color.WHITE;

        hex = hex.replace("#", "").trim();

        try {
            int rgb = Integer.parseInt(hex, 16);
            return Color.fromRGB(rgb);
        } catch (NumberFormatException e) {
            plugin.getLogger().warning("Ungültiger Hex-Farbcode in der Config: " + hex);
            return Color.WHITE;
        }
    }
}

