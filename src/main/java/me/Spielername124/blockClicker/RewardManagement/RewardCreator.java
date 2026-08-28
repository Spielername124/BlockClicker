package me.Spielername124.blockClicker.RewardManagement;

import me.Spielername124.blockClicker.RewardManagement.Rewards.CommandExecution.CommandExecution;
import me.Spielername124.blockClicker.RewardManagement.Rewards.ContainerSpawn.ContainerDrop;
import me.Spielername124.blockClicker.RewardManagement.Rewards.Effects.EffectReward;
import me.Spielername124.blockClicker.RewardManagement.Rewards.Entity.EntitySpawn;
import me.Spielername124.blockClicker.RewardManagement.Rewards.GuaranteedReward.GuaranteedReward;
import me.Spielername124.blockClicker.RewardManagement.Rewards.ItemDrop.ItemDrop;
import me.Spielername124.blockClicker.RewardManagement.Rewards.MobSpawn.MobSpawn;
import me.Spielername124.blockClicker.RewardManagement.Rewards.ReplaceBlock.SetBlock;
import me.Spielername124.blockClicker.RewardManagement.Rewards.Reward;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Map;

public class RewardCreator {
    public static Reward createReward(BlockClicker plugin, FileConfiguration config, Map<?, ?> rewardData){
        //iterate through the keywords of a reward to find out what kind of reward it is, creating a Reward of this kind when the keyword is found
        for (Object keyObj : rewardData.keySet()) {
            if (keyObj instanceof String key) {
                switch (key) {
                    case "item":
                        return new ItemDrop(plugin, config, rewardData);
                    case "container":
                        return new ContainerDrop(plugin, config, rewardData);
                    case "effect":
                        return new EffectReward(plugin, config, rewardData);
                    case "mob":
                        return new MobSpawn(plugin, config, rewardData);
                    case "command":
                        return new CommandExecution(plugin, config, rewardData);
                    case "guaranteed-reward":
                        return new GuaranteedReward(plugin, config, rewardData, 0);
                    case "set_block":
                        return new SetBlock(plugin, config, rewardData);
                    case "entity":
                        return new EntitySpawn(plugin, config, rewardData);
                }
            }
        }
        return null;
    }
}
