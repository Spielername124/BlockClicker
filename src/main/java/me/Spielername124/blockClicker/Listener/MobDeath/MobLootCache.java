package me.Spielername124.blockClicker.Listener.MobDeath;

import me.Spielername124.blockClicker.RewardManagement.Rewards.Reward;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MobLootCache {
    private final Map<String, List<Reward>> mobDrops = new HashMap<>();
    private final Map<String, Boolean> allowsNaturalDrops = new HashMap<>();


    public void clear(){
        mobDrops.clear();
        allowsNaturalDrops.clear();
    }

    public void registerMobFlags(String mobId, boolean allowsNaturalDrops){
        this.allowsNaturalDrops.put(mobId, allowsNaturalDrops);
    }

    public void registerMobDrop(String mobId, Reward reward) {

        if (!mobDrops.containsKey(mobId))
            mobDrops.put(mobId, new ArrayList<Reward>());

        mobDrops.get(mobId).add(reward);
    }

    public boolean getAllowsNaturalDrops(String mobId){return allowsNaturalDrops.getOrDefault(mobId, true);}

    public List<Reward> getRewardList(String mobId) {
        return mobDrops.getOrDefault(mobId, new ArrayList<>());
    }
}
