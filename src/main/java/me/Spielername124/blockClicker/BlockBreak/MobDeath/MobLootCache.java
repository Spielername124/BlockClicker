package me.Spielername124.blockClicker.BlockBreak.MobDeath;

import me.Spielername124.blockClicker.BlockBreak.RewardManagement.Rewards.PossibleItemStacks.DroppedItem;
import me.Spielername124.blockClicker.BlockBreak.RewardManagement.Rewards.Reward;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MobLootCache {
    private final Map<String, List<MobLoot>> mobDrops = new HashMap<>();
    private final Map<String, Boolean> allowsNaturalDrops = new HashMap<>();


    public void clear(){
        mobDrops.clear();
        allowsNaturalDrops.clear();
    }

    public void registerMobFlags(String mobId, boolean allowsNaturalDrops){
        this.allowsNaturalDrops.put(mobId, allowsNaturalDrops);
    }

    public void registerMobDrop(String mobId, MobLoot reward) {

        if (!mobDrops.containsKey(mobId))
            mobDrops.put(mobId, new ArrayList<MobLoot>());

        List<MobLoot> mobDropList = mobDrops.get(mobId);

        mobDropList.add(reward);
    }

    public boolean getAllowsNaturalDrops(String mobId){return allowsNaturalDrops.getOrDefault(mobId, true);}

    public List<MobLoot> getRewardList(String mobId) {
        return mobDrops.getOrDefault(mobId, new ArrayList<>());
    }
}
