package BlockBreak.RewardManagement;

import BlockBreak.RewardManagement.Rewards.Reward;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RewardCache {
    private final Map<String, Map<String, Map<Material, List<Reward>>>> savedRewards = new HashMap<>();

    public void clear(){
        savedRewards.clear();
    }

    public void registerReward(String zone, String toolGroup, Material material, Reward reward) {

        // get or initialize the tool group map
        Map<String, Map<Material, List<Reward>>> toolGroupMap = savedRewards.get(zone);
        if (toolGroupMap == null) {
            toolGroupMap = new HashMap<>();
            savedRewards.put(zone, toolGroupMap);
        }

        // get or initialize the material map
        Map<Material, List<Reward>> materialMap = toolGroupMap.get(toolGroup);
        if (materialMap == null) {
            materialMap = new HashMap<>();
            toolGroupMap.put(toolGroup, materialMap);
        }

        // get or initialize the list of compiled rewards
        List<Reward> rewardList = materialMap.get(material);
        if (rewardList == null) {
            rewardList = new ArrayList<>();
            materialMap.put(material, rewardList);
        }

        // save the reward
        rewardList.add(reward);
    }

    public List<Reward> getRewardList(String zone, String toolGroup, Material material) {
        Map<String, Map<Material, List<Reward>>> toolGroupMap = savedRewards.get(zone);
        if (toolGroupMap == null) {
            return new ArrayList<>(); // Safely yields an empty list
        }

        Map<Material, List<Reward>> materialMap = toolGroupMap.get(toolGroup);
        if (materialMap == null) {
            return new ArrayList<>();
        }

        List<Reward> rewardList = materialMap.get(material);
        if (rewardList == null) {
            return new ArrayList<>();
        }

        return rewardList;
    }
}
