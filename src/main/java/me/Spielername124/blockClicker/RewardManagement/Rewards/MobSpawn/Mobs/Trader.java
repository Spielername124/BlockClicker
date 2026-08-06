package me.Spielername124.blockClicker.RewardManagement.Rewards.MobSpawn.Mobs;

import me.Spielername124.blockClicker.BlockClicker;
import me.Spielername124.blockClicker.GlobalFlags;
import me.Spielername124.blockClicker.RewardManagement.Rewards.PossibleItemStacks.DroppedItem;
import org.bukkit.Location;
import org.bukkit.entity.AbstractVillager;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.MerchantRecipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Trader extends SpawnableMob{

    private final List<Trade> trades;



    public Trader(BlockClicker plugin, Map<?, ?> rewardData, EntityType type) {
        super(plugin, rewardData, type);

        trades = processTrades(plugin, rewardData);
    }

    //processes the rewardData to usable Trades
    private List<Trade> processTrades(BlockClicker plugin, Map<?, ?> rewardData){

        Object tradesObject = rewardData.get("trades");
        List<?> rawTrades = tradesObject instanceof List ? (List<?>) tradesObject : null;


        List<Trade> trades = new ArrayList<>();
        if(rawTrades == null) return trades;

        for(Object rawTrade : rawTrades){
            //if there is no valid trade continue, else get it
            if (!(rawTrade instanceof Map<?, ?> tradeMap)) continue;

            //checks if the output item is defined in the correct form of a map and creates the output item
            if(!(tradeMap.get("output") instanceof  Map<?, ?> outputMap)) continue;
            DroppedItem output = DroppedItem.create(plugin, outputMap);

            if(!(tradeMap.get("input1") instanceof  Map<?, ?> input1Map)) continue;
            DroppedItem input1 = DroppedItem.create(plugin, input1Map);

            //if either Input or output are null, is this not a valid trade
            if(output == null || input1 == null) continue;

            //set input2 if existing
            DroppedItem input2 = null;
            if((tradeMap.get("input2") instanceof  Map<?, ?> input2Map))
                 input2 = DroppedItem.create(plugin, input2Map);

            Object maxUsesRaw = tradeMap.get("max-uses");

            //todo make a configurable default flag?
            int maxUses = maxUsesRaw instanceof Number number? number.intValue(): 10;

            trades.add(new Trade(output, input1, input2, maxUses));
        }
        return trades;
    }


    @Override
    public void spawn(BlockClicker plugin, GlobalFlags flags, Location location){
        super.spawn(plugin, flags, location);
        if(spawnedMob==null) return;

        ArrayList<MerchantRecipe> recipes = new ArrayList<>();
        for(Trade trade : trades){
            recipes.add(trade.createRecipe());
        }
        //we know this cast will work, since this mob is only able to be a AbstractVillager
        if (spawnedMob instanceof Villager villager)
            villager.setProfession(Villager.Profession.FARMER);

        ((AbstractVillager) spawnedMob).setRecipes(recipes);
    }



}
