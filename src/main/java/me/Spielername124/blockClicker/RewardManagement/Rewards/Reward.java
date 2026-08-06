package me.Spielername124.blockClicker.RewardManagement.Rewards;

import me.Spielername124.blockClicker.EventWideFlags;
import me.Spielername124.blockClicker.GlobalFlags;
import me.Spielername124.blockClicker.RewardManagement.Rewards.RewardsHelper.Chance;
import me.Spielername124.blockClicker.BlockClicker;
import net.kyori.adventure.key.InvalidKeyException;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public abstract class Reward {
    public final FileConfiguration config;
    protected final BlockClicker plugin;
    protected final double chance;
    public final Map<?, ?> rewardData;
    protected final boolean isLuckDependent;

    private final Sound sound;
    private final int soundPriority;

    private final GlobalFlags flags;



    public Reward(BlockClicker plugin, FileConfiguration config, Map<?, ?> rewardData) {

        Number chanceNr = (Number) rewardData.get("chance");
        chance = chanceNr != null ? chanceNr.doubleValue() : 100;
        isLuckDependent = Boolean.TRUE.equals(rewardData.get("luck-dependence"));
        this.rewardData = rewardData;
        this.plugin = plugin;
        this.config = config;

        //handle the local flag overwrites
        flags = rewardData.get("local-flags") instanceof Map<?, ?> localFlags ?
                new GlobalFlags(plugin.flags, localFlags):
                plugin.flags;

        //set the sound data for the reward
        String soundSt = (String) rewardData.get("sound");
        Number priorityNr = (Number) rewardData.get("sound-priority");
        this.soundPriority = priorityNr != null ? priorityNr.intValue() : 0;

        if (soundSt == null || soundSt.isBlank()) {
            sound = null;
            return;
        }
        Sound localSound = null;
        try {
            Key soundKey = Key.key(soundSt.trim().toLowerCase());
            localSound = Sound.sound(soundKey, Sound.Source.MASTER, 1.0f, 1.0f);
        } catch (InvalidKeyException e) {
            plugin.getLogger().severe("The sound '" + soundSt + "' in your config has invalid characters!");
        }






        this.sound = localSound;
    }

    public final void rollAndExecute(Player player, Location location, RewardSound sound, ItemStack toolUsed, Block block, EventWideFlags eventWideFlags) {

        //roll if the reward is granted, return if not
        if(!Chance.performDropRoll(flags, chance, toolUsed, player, block, isLuckDependent))
            return;

        execute(player, location, flags, sound, toolUsed, block, eventWideFlags);

       sound.setSound(this.sound, soundPriority);
    }

    protected abstract void execute(Player player, Location location, GlobalFlags flags, RewardSound sound, ItemStack toolUsed, Block block, EventWideFlags eventWideFlags);
}
