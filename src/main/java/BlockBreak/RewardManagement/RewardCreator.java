package BlockBreak.RewardManagement;

import BlockBreak.RewardManagement.Rewards.CommandExecution.CommandExecution;
import BlockBreak.RewardManagement.Rewards.ContainerSpawn.ContainerDrop;
import BlockBreak.RewardManagement.Rewards.Effects.EffectReward;
import BlockBreak.RewardManagement.Rewards.GuaranteedReward.GuaranteedReward;
import BlockBreak.RewardManagement.Rewards.ItemDrop.ItemDrop;
import BlockBreak.RewardManagement.Rewards.MobSpawn.MobSpawn;
import BlockBreak.RewardManagement.Rewards.Reward;
import BlockBreak.RewardManagement.Rewards.RewardSound;
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
                }
            }
        }
        return null;
    }
}
