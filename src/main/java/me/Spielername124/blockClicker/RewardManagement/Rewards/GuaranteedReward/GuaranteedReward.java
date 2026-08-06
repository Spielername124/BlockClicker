package me.Spielername124.blockClicker.RewardManagement.Rewards.GuaranteedReward;

import me.Spielername124.blockClicker.GlobalFlags;
import me.Spielername124.blockClicker.RewardManagement.RewardCreator;
import me.Spielername124.blockClicker.RewardManagement.Rewards.Reward;
import me.Spielername124.blockClicker.RewardManagement.Rewards.RewardSound;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class GuaranteedReward extends Reward {
    private final int recursionDepth;
    private final List<Reward> guaranteedRewardPool = new ArrayList<>();
    private final List<Double> weights = new ArrayList<>();
    private double totalWeight = 0.0;

    public GuaranteedReward(BlockClicker plugin, FileConfiguration config, Map<?, ?> rewardData, int recursionDepth) {
        super(plugin, config, rewardData);

        this.recursionDepth = recursionDepth;

        //create the lootPool on creation of this instance
        Object rawList = rewardData.get("guaranteed-reward");
        if (rawList instanceof List) {
            for (Object obj : (List<?>) rawList) {
                if (obj instanceof Map<?, ?> innerData) {

                    //create the rewards
                    Reward compiledReward = RewardCreator.createReward(plugin, config, innerData);

                    if (compiledReward != null) {
                        guaranteedRewardPool.add(compiledReward);
                        // Extract and cache the weight
                        Number weightNr = (Number) innerData.get("weight");
                        double weight = weightNr != null ? weightNr.doubleValue() : 1.0;
                        weights.add(weight);
                        totalWeight += weight;
                    }
                }
            }
        }
    }

    @Override
    protected void execute(Player player, Location location, GlobalFlags flags, RewardSound sound, ItemStack toolUsed, Block block){
        Reward reward = getChosenReward(flags);
        if(reward!=null)
            reward.rollAndExecute(player, location, sound, toolUsed, block);
    }

    public Reward getChosenReward (GlobalFlags flags){
        if (guaranteedRewardPool.isEmpty())return null;

        if (recursionDepth > flags.recursionDepth) {
            plugin.getLogger().warning("Maximum recursion depth of " + recursionDepth + " reached. Recursion prevention measures were taken by cancelling this reward. If you are certain that no recursion exists, increase the cap");
            return null;
        }

        //roll a weight
        double rolledWeight = ThreadLocalRandom.current().nextDouble(totalWeight);
        double currentWeight = 0.0;

        //iterates through the pool until it finds the one that was rolled
        for (int i = 0; i < guaranteedRewardPool.size(); i++) {
            currentWeight += weights.get(i);
            if (currentWeight >= rolledWeight) {
                return guaranteedRewardPool.get(i);

            }
        }
        return null;
    }
}
