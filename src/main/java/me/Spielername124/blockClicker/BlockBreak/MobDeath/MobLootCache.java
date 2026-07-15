package me.Spielername124.blockClicker.BlockBreak.MobDeath;

import me.Spielername124.blockClicker.BlockBreak.RewardManagement.Rewards.PossibleItemStacks.DroppedItem;
import me.Spielername124.blockClicker.BlockBreak.RewardManagement.Rewards.Reward;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MobLootCache {
    private final Map<String, List<DroppedItem>> mobDrops = new HashMap<>();

    public void clear(){
        mobDrops.clear();
    }

    public void registerMobDrop(String mobId, DroppedItem reward) {

        if (mobDrops.containsKey("mobId"))
            mobDrops.put(mobId, new ArrayList<DroppedItem>());

        List<DroppedItem> mobDropList = mobDrops.get(mobId);

        mobDropList.add(reward);
    }

    public List<DroppedItem> getRewardList(String mobId) {
        return mobDrops.get(mobId);
    }
}
