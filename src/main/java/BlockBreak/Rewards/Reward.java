package BlockBreak.Rewards;

import BlockBreak.GlobalFlags;
import BlockBreak.Rewards.RewardsHelper.Chance;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public abstract class Reward {
    public final FileConfiguration config;
    protected final BlockClicker plugin;
    protected final double chance;
    protected final RewardSound  sound;
    protected final Map<?, ?> rewardData;
    protected final boolean isLuckDependent;

    public Reward(BlockClicker plugin, FileConfiguration config, Map<?, ?> rewardData, RewardSound sound) {

        Number chanceNr = (Number) rewardData.get("chance");
        chance = chanceNr != null ? chanceNr.doubleValue() : 100;
        isLuckDependent = Boolean.TRUE.equals(rewardData.get("luck-dependence"));

        this.sound = sound;
        this.rewardData = rewardData;
        this.plugin = plugin;
        this.config = config;
    }

    public final void rollAndExecute(Player player, Location location, GlobalFlags flags, ItemStack toolUsed, Block block) {

        //roll if the reward is granted, return if not
        if(!Chance.performDropRoll(flags, chance, toolUsed, player, isLuckDependent))
            return;

        execute(player, location, flags, toolUsed, block);

       sound.setSound(rewardData);
    }

    protected abstract void execute(Player player, Location location, GlobalFlags flags, ItemStack toolUsed, Block block);
}
